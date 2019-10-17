package org.tea;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.tea.en.WordSenser;
import org.tea.en.WordUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static junit.framework.TestCase.assertEquals;

public class WordSenserTest {

    @Test
    public void testFilterSmallText() throws Exception {
        String s = FileUtils.readFileToString(new File("src/test/en/Small_geeks.txt"), StandardCharsets.UTF_8);
        LinkedHashMap<String, Long> stat = WordUtils.computeWordsFrequencyOrdered(s);

        WordSenser.inFewWords(stat);

        assertEquals(6, stat.get("geeks").longValue());
        assertEquals(3, stat.get("article").longValue());
        assertEquals(2, stat.get("contribute").longValue());
        assertEquals(3, stat.size());
    }

    @Test
    public void testFilterMediumText() throws Exception {
        String s = FileUtils.readFileToString(new File("src/test/en/Large_oop.txt"), StandardCharsets.UTF_8);
        LinkedHashMap<String, Long> stat = WordUtils.computeWordsFrequencyOrdered(s);

        WordSenser.inFewWords(stat);

        assertEquals(110, stat.get("oop").longValue());
        assertEquals(32, stat.get("functional").longValue());
        assertEquals(33, stat.size());
    }

    @Test
    public void testFilterMedium2Text() throws Exception {
        String s = FileUtils.readFileToString(new File("src/test/en/Medium_parents.txt"), StandardCharsets.UTF_8);
        LinkedHashMap<String, Long> stat = WordUtils.computeWordsFrequencyOrdered(s);

        WordSenser.inFewWords(stat);

        assertEquals(17, stat.get("parents").longValue());
        assertEquals(8, stat.get("media").longValue());
        assertEquals(31, stat.size());
    }

    @Test
    public void testFilterDebug() throws Exception {
//        String s = FileUtils.readFileToString(new File("src/test/en/Small_geeks.txt"), StandardCharsets.UTF_8);
//        String s = FileUtils.readFileToString(new File("src/test/en/Medium_parents.txt"), StandardCharsets.UTF_8);
//        String s = FileUtils.readFileToString(new File("src/test/en/Large_oop.txt"), StandardCharsets.UTF_8);
        String s = FileUtils.readFileToString(new File("src/test/en/XLarge_films.txt"), StandardCharsets.UTF_8);
        LinkedHashMap<String, Long> stat = WordUtils.computeWordsFrequencyOrdered(s);

        WordSenser.inFewWords(stat);

        System.out.println("===");
        stat.forEach((k, v) -> System.out.println(k + " " + v));
        assertEquals(31, 31);

    }

}
