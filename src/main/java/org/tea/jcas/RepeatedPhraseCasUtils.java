package org.tea.jcas;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.ProcessTrace;
import org.tea.PhraseGrouping;
import org.tea.RepeatedWordAnnotator;
import org.tea.entity.Phrase;
import org.tea.experiment.LCS2;
import org.tea.uima.types.RepeatedWord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.uima.fit.factory.JCasFactory.createJCas;
import static org.apache.uima.fit.util.JCasUtil.select;
/*
* https://dzone.com/articles/longest-common-substring
* https://www.geeksforgeeks.org/print-longest-common-substring/
 */
public class RepeatedPhraseCasUtils {
    private RepeatedPhraseCasUtils() {
    }

    private static final String PHRASE_SEPARATOR = "\b";

    public static Map<String, List<Phrase>> computePhrasesFrequency(String tea) {
        return computePhrasesFrequency(tea, PhraseGrouping.CASE_SENSITIVE);
    }
    /*
     *  Found repetitive phrases in text
     * todo: review!
     */
    public static Map<String, List<Phrase>> computePhrasesFrequency(String tea, PhraseGrouping grouping) {
        switch   (grouping){
            case CASE_INSENSITIVE:
                tea = tea.toLowerCase();
                break;
            case FIRST_SENTENCE_LETTER_LOWERCASE:
                // todo:
            case CASE_SENSITIVE:
        }

        JCas jCas = null;
        try {
            jCas = createJCas();
            jCas.setDocumentText(tea);
            AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(RepeatedWordAnnotator.class);
            ProcessTrace process = analysisEngine.process(jCas);
            System.out.println("==Analysis duration(ms): " + process.getEvent(RepeatedWordAnnotator.class.getName(), "Analysis").getDuration());
        } catch (ResourceInitializationException|CASException| AnalysisEngineProcessException e) {
            e.printStackTrace();
            System.err.println("Error occurred: " + e.getMessage());
        }

        Collection<RepeatedWord> select = select(jCas, RepeatedWord.class);
        List<Phrase> phrases = makePhrases(select);

        Map<Phrase, List<Phrase>> phrasesLinks = new LinkedHashMap<>();
        for (int idx = 0; idx < phrases.size(); idx++) {
            Phrase current = phrases.get(idx);
            int maxCommonChars = 0;
            for (int i = 0; i < phrases.size(); i++) {
                if (idx == i) {
                    continue; // skip the same phrase
                }
                Phrase toCompare = phrases.get(i);
                String lcs = LCS2.printLCSubStr(current.getText().toString(), toCompare.getText().toString());
                int nChars = lcs.length();
                if (current.getPhraseWords().size() == 2 && nChars < current.getText().length()) {
                    continue; // skip one-word overlap
                }
                if (nChars == maxCommonChars) { // we have repetitive phase
                    phrasesLinks.computeIfAbsent(current, k -> new ArrayList<>()).add(toCompare);
                } else if (nChars > maxCommonChars) {
                    maxCommonChars = nChars;
                    current.setLcs(lcs);
                    phrasesLinks.put(current, new ArrayList<>(Arrays.asList(toCompare)));
                }
            }
        }

        Set<Phrase> result = new HashSet<>();
        for (Map.Entry<Phrase, List<Phrase>> entry : phrasesLinks.entrySet()) {
            Phrase key = entry.getKey();
            result.add(extractRepeatedPhrase(key, key.getLcs()));
            for (Phrase phrase : entry.getValue()) {
                result.add(extractRepeatedPhrase(phrase, key.getLcs()));
            }
        }

        Map<String, List<Phrase>> phrasesStat = new LinkedHashMap<>();
        for (Phrase ph : result) {
            phrasesStat.computeIfAbsent(ph.getText().toString(), k -> new ArrayList<>()).add(ph);
        }

        return phrasesStat.entrySet().stream().
                filter(entry -> entry.getValue().size() > 1 && entry.getValue().get(0).getPhraseWords().size() > 1).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static List<Phrase> makePhrases(Collection<RepeatedWord> select) {
        // todo: we hope that space char length is 1!
        List<Phrase> phrases = new ArrayList<>();
        Phrase newPhrase = new Phrase();
        RepeatedWord prev = null;
        boolean isPrevAdded = false;
        StringBuilder allPhrasesParts = new StringBuilder();
        for (RepeatedWord rw : select) {
            if (prev != null && rw.getBegin() - prev.getEnd() == 1) {
                if (!isPrevAdded) {
                    newPhrase.addWord(prev);
                }
                newPhrase.addWord(rw);
                isPrevAdded = true;
            } else {
                if (isPrevAdded) {
                    // close phraseWords
                    if (!isWordWithPunctuation(newPhrase)) {
                        phrases.add(newPhrase);
                        allPhrasesParts.append(newPhrase.getText()).append(PHRASE_SEPARATOR);
                    }
                    newPhrase = new Phrase();
                }
                isPrevAdded = false;
            }
            prev = rw;
        }

        removeAbandonedPhrases(phrases, allPhrasesParts);
        return phrases;
    }

    private static void removeAbandonedPhrases(List<Phrase> phrases, StringBuilder allPhrasesParts) {
        for (Iterator<Phrase> it = phrases.iterator(); it.hasNext(); ) {
            Phrase next = it.next();
            String str = next.getText().toString() + PHRASE_SEPARATOR;
            if (next.getPhraseWords().size() == 2) {
                int idx = allPhrasesParts.indexOf(str);
                allPhrasesParts.delete(idx, idx + str.length());
                if (allPhrasesParts.indexOf(str.replace(PHRASE_SEPARATOR, "")) < 0) {
                    it.remove();
                }
            }
        }
    }

    private static boolean isWordWithPunctuation(Phrase phrase) {
        return phrase.getPhraseWords().size() == 2 &&
                // todo: refactor me
                (phrase.getText().indexOf("," + Phrase.WORD_SEPARATOR) > -1 ||
                        phrase.getText().indexOf(")" + Phrase.WORD_SEPARATOR) > -1);
    }

    private static Phrase extractRepeatedPhrase(Phrase key, String lcs) {
        int comIdx = key.getText().indexOf(lcs);
        int phraseBeginIdx = 0;
        int phraseEndIdx = comIdx + lcs.length();
        Phrase repeatedPhrase = new Phrase();
        for (RepeatedWord phraseWord : key.getPhraseWords()) {
            if (phraseBeginIdx >= comIdx && phraseBeginIdx <= phraseEndIdx) {
                repeatedPhrase.addWord(phraseWord);
            }
            phraseBeginIdx += phraseWord.getCoveredText().length() + 1;
        }
        return repeatedPhrase;
    }

}
