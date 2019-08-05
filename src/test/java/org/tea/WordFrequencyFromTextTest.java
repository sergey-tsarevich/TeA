package org.tea;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;

/**
 * Sample tests for the example component. Remove these and add new tests.
 */
public class WordFrequencyFromTextTest {

    static String text = "Welcome to the world of Geeks \n" +
            "This portal has been created to provide well written well thought and well explained \n" +
            "solutions for selected questions If you like Geeks for Geeks and would like to contribute \n" +
            "here is your chance You can write article and mail your article to contribute at \n" +
            "geeksforgeeks org See your article appearing on the Geeks for Geeks main page and help \n" +
            "thousands of other Geeks";


    @Test
    public void streamsSolution() {
        Stream<String> stream = Stream.of(text.toLowerCase().split("\\W+")).sequential();
                //.parallel();

        Map<String, Long> wordFreq = stream.collect(Collectors.groupingBy(String::toString, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
        assertText(wordFreq);
    }

    private void assertText(Map<String, Long> wordFreq) {
        assertEquals(6, wordFreq.get("geeks").longValue());
        assertEquals(4, wordFreq.get("and").longValue());
        assertEquals(4, wordFreq.get("to").longValue());
    }

    @Test
    public void mapSolution() {
        Map<String, Long> map = new HashMap<>();
        String[] words = text.toLowerCase().split("\\W+");
        for (String w : words) {
            Long n = map.get(w);
            n = (n == null) ? 1 : ++n;
            map.put(w, n);
        }

        Map<String, Long> resultMap = map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        assertText(resultMap);
    }

}
