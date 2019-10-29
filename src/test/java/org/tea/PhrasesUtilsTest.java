package org.tea;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LongestCommonSubsequence;
import org.apache.commons.text.similarity.LongestCommonSubsequenceDistance;
import org.junit.Ignore;
import org.junit.Test;
import org.tea.en.PhrasesUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;

public class PhrasesUtilsTest {

    @Test
    public void smallText() throws IOException {
        String s = FileUtils.readFileToString(new File("src/test/en/Small_geeks.txt"), StandardCharsets.UTF_8);
        LinkedHashMap<String, Integer> stat = PhrasesUtils.computePhrasesFrequencyOrdered(s);

        assertEquals(2, stat.get("your article").intValue());
        assertEquals(2, stat.get("geeks for geeks").intValue());
        assertEquals(2, stat.get("to contribute").intValue());
        assertEquals(3, stat.size());
    }

    @Test
    public void test() throws IOException {
        String s = FileUtils.readFileToString(new File("src/test/en/Medium_parents.txt"), StandardCharsets.UTF_8);
//        String s = FileUtils.readFileToString(new File("src/test/en/Large_oop.txt"), StandardCharsets.UTF_8);
//        String s = FileUtils.readFileToString(new File("src/test/en/XLarge_films.txt"), StandardCharsets.UTF_8); // takes 32s!


        LinkedHashMap<String, Integer> stat = PhrasesUtils.computePhrasesFrequencyOrdered(s);

        System.out.println("===");
        stat.forEach((k, v) -> System.out.println(k + " " + v));
        assertEquals(31, 31);
    }

}
