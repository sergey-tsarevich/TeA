package org.tea.en;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class PhrasesUtils {

    private PhrasesUtils() {
    }

    /*
     *  Found repetitive phrases in text
     * todo: find maximum phrase of three words and phrases have a lot intersections!
     */
    public static LinkedHashMap<String, Integer> computePhrasesFrequencyOrdered(String tea) {
        tea = tea.toLowerCase().replaceAll("\\W+", " "); // noramalization

        String[] words = tea.split(" ");

        Map<String, Integer> repeatedPhrases = new HashMap<>();

        String prevWord = null;
        String prevPhrase = null;
        int index = 0;
        for (String word : words) {
            index += word.length() + 1; // considering space
            if (prevWord != null) {
                String phrase = prevWord + " " + word;
                if (index < tea.length() &&
                        tea.substring(index).contains(phrase)) { // find the next nearest occurrence
                    Integer n = repeatedPhrases.get(prevPhrase);
                    if (n != null) {
                        if (n == 2) repeatedPhrases.remove(prevPhrase); ///so, it's 3-words phrase
                        String longPhrase = prevPhrase + " " + word;
                        repeatedPhrases.compute(longPhrase, (k, v) -> v == null ? 2 : v + 1);
                    } else {
                        repeatedPhrases.compute(phrase, (k, v) -> v == null ? 2 : v + 1);
                    }
                }
                prevPhrase = phrase;
            }
            prevWord = word;
        }

        return repeatedPhrases.entrySet()
                .stream()
                .sorted(reverseOrder(comparingByValue()))
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));
    }



}
