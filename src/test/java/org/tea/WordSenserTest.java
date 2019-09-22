package org.tea;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.tea.en.WordSenser;
import org.tea.en.WordUtils;

import java.io.File;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class WordSenserTest {

    @Test
    public void testFilterSmallText() throws Exception {
        String s = FileUtils.readFileToString(new File("src/test/en/small_test_EN.txt"));
        Map<String, Long> stat = WordUtils.computeWordsFrequencyOrdered(s);

        WordSenser.inFewWords(stat);

        assertEquals(6, stat.get("geeks").longValue());
        assertEquals(3, stat.get("article").longValue());
        assertEquals(2, stat.get("contribute").longValue());
        assertEquals(3, stat.size());
    }

    @Test
    public void testFilterMediumText() throws Exception {
        String s = FileUtils.readFileToString(new File("src/test/en/medium_test_EN.txt"));
        Map<String, Long> stat = WordUtils.computeWordsFrequencyOrdered(s);

        WordSenser.inFewWords(stat);

        assertEquals(110, stat.get("oop").longValue());
        assertEquals(32, stat.get("functional").longValue());
        assertEquals(33, stat.size());
    }

    @Test
    public void testFilterMedium2Text() throws Exception {
        String s = FileUtils.readFileToString(new File("src/test/en/most-parents-never-check-their-children-devices.txt"));
        Map<String, Long> stat = WordUtils.computeWordsFrequencyOrdered(s);

        WordSenser.inFewWords(stat);

        assertEquals(17, stat.get("parents").longValue());
        assertEquals(8, stat.get("media").longValue());
        assertEquals(31, stat.size());
    }

    @Test
    public void testFilterDebug() throws Exception {
//        String s = FileUtils.readFileToString(new File("src/test/en/small_test_EN.txt"));
        String s = FileUtils.readFileToString(new File("src/test/en/film_chats.txt"));
//        String s = FileUtils.readFileToString(new File("src/test/en/medium_test_EN.txt"));
//        String s = FileUtils.readFileToString(new File("src/test/en/tumblr.txt"));
//        String s = FileUtils.readFileToString(new File("src/test/en/most-parents-never-check-their-children-devices.txt"));
        Map<String, Long> stat = WordUtils.computeWordsFrequencyOrdered(s);

        WordSenser.inFewWords(stat);

        System.out.println("===");
        stat.forEach((k, v) -> System.out.println(k + " " + v));
        assertEquals(31, 31);

    }

}
