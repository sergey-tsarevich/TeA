package org.tea;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.tea.en.PhrasesUtils;
import org.tea.en.WordSenser;
import org.tea.en.WordUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class PhrasesUtilsTest {
// regex: (?s)([A-Z]+)\s+.*?\1
//    (?s)\b(\w+(?:\s*\w*))\s+.*?\1
//        \b(\w+(?:\s*\w*))\s+\1\b
//RegEx: max 2-words the nearest phrases


    @Test
    public void smallText() throws IOException {
        String s = FileUtils.readFileToString(new File("src/test/en/Small_geeks.txt"));
        LinkedHashMap<String, Integer> stat = PhrasesUtils.computePhrasesFrequencyOrdered(s);

        assertEquals(2, stat.get("your article").intValue());
        assertEquals(2, stat.get("geeks for geeks").intValue());
        assertEquals(2, stat.get("to contribute").intValue());
        assertEquals(3, stat.size());
    }

    @Test
    // todo:
    public void mediumTextLongestAndFrequentPhrase() throws IOException {
        String s = FileUtils.readFileToString(new File("src/test/en/Medium_parents.txt"));
        LinkedHashMap<String, Integer> stat = PhrasesUtils.computePhrasesFrequencyOrdered(s);
        assertEquals(2, stat.get("three out of five parents have never checked their child's devices zdnet").intValue());
        assertEquals(2, stat.get("only 14% of parents admitted to regularly checking their child's devices").intValue());
        assertEquals(4, stat.get("when it comes to").intValue());
        assertEquals(6, stat.get("their child's devices").intValue());
        assertEquals(8, stat.get("social media").intValue());
    }

    @Test
    public void test() throws IOException {
        String s = FileUtils.readFileToString(new File("src/test/en/Medium_parents.txt"));
//        String s = FileUtils.readFileToString(new File("src/test/en/Large_oop.txt"));
//        String s = FileUtils.readFileToString(new File("src/test/en/XLarge_films.txt")); // takes 32s!


        LinkedHashMap<String, Integer> stat = PhrasesUtils.computePhrasesFrequencyOrdered(s);

        System.out.println("===");
        stat.forEach((k, v) -> System.out.println(k + " " + v));
        assertEquals(31, 31);
    }

}
