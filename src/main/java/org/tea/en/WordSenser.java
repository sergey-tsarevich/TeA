package org.tea.en;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
    To make presentation of the shortest sense of the text
    // filter top 20% of words with more than 1 occurrence
 */
public class WordSenser {

    private static final String LIB_EN_MOST_COMMON_WORDS = "lib_en/most_common_words.txt";
    private static final String LIB_EN_MOST_COMMON_PHRASES = "lib_en/most_common_phrases.txt";
    private static final double TOP_PERCENTS_TO_LEAVE = 0.2;
    private static final int MIN_WORD_FREQUENCY = 1;
    private static final int MAX_WORDS_TO_LEAVE = 33;

    private WordSenser() {
    }

    public static void inFewWords(Map<String, Long> wordsStat) throws IOException {
        filter(wordsStat);
        removeCommonWords(wordsStat);
        limit(wordsStat);
    }

    // filter top 20% of words with more than 1 occurrence
    public static void filter(Map<String, Long> wordsStat) {
        Iterator<Map.Entry<String, Long>> iterator = wordsStat.entrySet().iterator();
        int idx = 0;
        while (iterator.hasNext()) {
            idx++;
            Map.Entry<String, Long> wordStat = iterator.next();
            if (idx > (wordsStat.size() * TOP_PERCENTS_TO_LEAVE) ||
                    wordStat.getValue() <= MIN_WORD_FREQUENCY) {
                iterator.remove();
            }
        }
    }

    public static void removeCommonWords(Map<String, Long> wordsStat) throws IOException {
        // todo: make sense for only for nouns and verbs?!
        List<String> englishWords = Files.readAllLines(Paths.get(LIB_EN_MOST_COMMON_WORDS));
        wordsStat.keySet().removeAll(englishWords);
        List<String> englishPhrases = Files.readAllLines(Paths.get(LIB_EN_MOST_COMMON_PHRASES));
        wordsStat.keySet().removeAll(englishPhrases);
    }

    public static void limit(Map<String, Long> wordsStat) {
        if (wordsStat.size() > MAX_WORDS_TO_LEAVE) {
            Iterator<Map.Entry<String, Long>> iterator = wordsStat.entrySet().iterator();
            int idx = 0;

            while (iterator.hasNext()) {
                idx++;
                iterator.next();
                if (idx > MAX_WORDS_TO_LEAVE) {
                    iterator.remove();
                }
            }
        }
    }
}
