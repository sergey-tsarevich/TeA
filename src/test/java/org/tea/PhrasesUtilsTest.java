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
    public void test() throws IOException {
        String s = FileUtils.readFileToString(new File("src/test/en/small_test_EN.txt"));
//        String s = FileUtils.readFileToString(new File("src/test/en/film_chats.txt")); // takes 32s!
//        String s = FileUtils.readFileToString(new File("src/test/en/medium_test_EN.txt"));
//        String s = FileUtils.readFileToString(new File("src/test/en/tumblr.txt"));
//        String s = FileUtils.readFileToString(new File("src/test/en/most-parents-never-check-their-children-devices.txt"));


        LinkedHashMap<String, Integer> stat = PhrasesUtils.computePhrasesFrequencyOrdered(s);

        System.out.println("===");
        stat.forEach((k, v) -> System.out.println(k + " " + v));
        assertEquals(31, 31);
    }

}
