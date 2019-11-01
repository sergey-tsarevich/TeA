package org.tea;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.tea.en.PhrasesUtils;
import org.tea.entity.Phrase;
import org.tea.jcas.RepeatedPhraseCasUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;
import static junit.framework.TestCase.assertEquals;

public class RepeatedPhraseCasUtilsTest {

    @Test
    public void smallText() throws IOException {
        String s = FileUtils.readFileToString(new File("src/test/en/Small_geeks.txt"), StandardCharsets.UTF_8);
        Map<String, List<Phrase>> stat = RepeatedPhraseCasUtils.computePhrasesFrequency(s);

        assertEquals(2, stat.get("your article").size());
        assertEquals(2, stat.get("Geeks for Geeks").size());
        assertEquals(2, stat.get("to contribute").size());
        assertEquals(3, stat.size());
    }

    @Test
    // todo: test commented!
    public void mediumTextLongestAndFrequentPhrase() throws IOException {
        String s = FileUtils.readFileToString(new File("src/test/en/Medium_parents.txt"), StandardCharsets.UTF_8);
        Map<String, List<Phrase>> stat = RepeatedPhraseCasUtils.computePhrasesFrequency(s, PhraseGrouping.CASE_INSENSITIVE);
        assertEquals(2, stat.get(". three out of five parents have never checked their child's devices zdnet rs components").size());
        assertEquals(2, stat.get("only 14% of parents admitted to regularly checking their child's devices").size());
        assertEquals(4, stat.get("when it comes to").size());
        assertEquals(6, stat.get("their child's devices").size());
//        assertEquals(8, stat.get("social media").size());
    }


    @Test
    public void debugText() throws IOException {
//        String s = FileUtils.readFileToString(new File("src/test/en/Small_geeks.txt"), StandardCharsets.UTF_8);
        String s = FileUtils.readFileToString(new File("src/test/en/Medium_parents.txt"), StandardCharsets.UTF_8);
        Map<String, List<Phrase>> stat = RepeatedPhraseCasUtils.computePhrasesFrequency(s, PhraseGrouping.CASE_INSENSITIVE);

        stat.entrySet().stream()
                .sorted(reverseOrder((c1, c2) -> {
                    int compare = Integer.compare(c1.getValue().size(), c2.getValue().size());
                    if (compare == 0) {
                        return Integer.compare(c1.getKey().length(), c2.getKey().length());
                    } else {
                        return compare;
                    }
                }))
//                .sorted(reverseOrder(Comparator.comparingInt(c -> c.getValue().size())))
                .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue().size()));

    }

}
