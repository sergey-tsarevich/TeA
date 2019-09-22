package org.tea;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.tea.en.WordUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

/**
 * Sample tests for the example component. Remove these and add new tests.
 */
public class WordFrequencyFromTextTest {

    private String text = "";

    @Before
    public void setUp() throws Exception {
        text = FileUtils.readFileToString(new File("src/test/en/small_test_EN.txt"));
    }

    @Test
    public void streamsSolution() { //~ 2ms
        Map<String, Long> wordFreq = WordUtils.computeWordsFrequency(text);
        assertText(wordFreq);
    }

    @Test
    public void streamsSolutionOrdered() { //~ 2ms
        Map<String, Long> wordFreq = WordUtils.computeWordsFrequencyOrdered(text);
        assertText(wordFreq);
    }

    private void assertText(Map<String, Long> wordFreq) {
        assertEquals(6, wordFreq.get("geeks").longValue());
        assertEquals(4, wordFreq.get("and").longValue());
        assertEquals(4, wordFreq.get("to").longValue());
    }

    @Test
    public void mapSolution() { //~ 7ms
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

    @Test
    public void mapSolution2() { //~ 11ms
        Map<String, Long> map = new HashMap<>();
        String[] words = text.toLowerCase().split("\\W+");
        for (String w : words) {
            map.compute(w, (k, v) -> v == null ? 1 : v + 1);
        }

        Map<String, Long> resultMap = map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        assertText(resultMap);
    }

    @Test
    public void arraysStream() {//~ 67ms
        String[] words = text.toLowerCase().split("\\W+");
        Map<String, Long> result = Arrays.stream(words)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

        assertText(result);
    }

    @Test
    public void WordsFrequency() throws Exception{
        String s = FileUtils.readFileToString(new File("src/test/en/medium_test_EN.txt"));

        Map<String, Long> x = WordUtils.computeWordsFrequencyOrdered(s);

        assertEquals(67, x.get("code").longValue()); // "code-sharing" is a word
    }

}
