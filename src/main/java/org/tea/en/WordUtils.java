package org.tea.en;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.groupingBy;

public class WordUtils {

//    public static final String WORD_SPLIT_PATTERN = "\\W+"; // we loose a lot of terms with this pattern(
    private static final String WORD_SPLIT_PATTERN = "[\\s.,?!()—…]+";

    private WordUtils() {
    }

    public static Map<String, Long> computeWordsFrequency(String tea) {
        Stream<String> stream = Stream.of(tea.toLowerCase().split(WORD_SPLIT_PATTERN));

        return stream.collect(groupingBy(x -> x, counting()));
    }

    /**
     * The same as {@link #computeWordsFrequency(String)} but ordered
     */
    public static LinkedHashMap<String, Long> computeWordsFrequencyOrdered(String tea) {
        return computeWordsFrequency(tea).entrySet().stream()
                .sorted(reverseOrder(comparingByValue()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> {
                            throw new IllegalStateException(String.format("Duplicate key for values %s and %s", v1, v2));
                        },
                        LinkedHashMap::new));
    }


}