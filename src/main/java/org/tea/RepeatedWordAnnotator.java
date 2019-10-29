package org.tea;

import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.tea.uima.types.RepeatedWord;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Used to annotate RepeatedPhrases.
 */
@TypeCapability(outputs = {"org.tea.uima.types.RepeatedWord"})
public class RepeatedWordAnnotator
        extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {

    /** The Constant wordBreak. */
    private static final BreakIterator wordBreak = BreakIterator.getWordInstance(Locale.US);

    @Override
    public void process(JCas jCas) {
        String input = jCas.getDocumentText();
        Map<String, Long> stat = new HashMap<>();
        List<RepeatedWord> words = new ArrayList<>();

        wordBreak.setText(input);
        for (int end = wordBreak.next(), start = wordBreak.first(); end != BreakIterator.DONE; start = end, end = wordBreak
                .next()) {
            // eliminate all-whitespace tokens
            boolean isWhitespace = true;
            for (int i = start; i < end; i++) {
                if (!Character.isWhitespace(input.charAt(i))) {
                    isWhitespace = false;
                    break;
                }
            }
            if (!isWhitespace) {
                RepeatedWord aWord = new RepeatedWord(jCas, start, end);
                words.add(aWord);
                // todo: remove punctuation???
                stat.compute(aWord.getCoveredText(), (k, v) -> v == null ? 1 : v + 1);
            }
        }
        words.removeIf(w -> stat.get(w.getCoveredText()) < 2);
        for (RepeatedWord word : words) {
//            System.out.println(word.getCoveredText());
            word.addToIndexes();
        }
    }
}
