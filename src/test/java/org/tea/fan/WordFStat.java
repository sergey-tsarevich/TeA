package org.tea.fan;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/*
    Effective java
 */
public class WordFStat {

    public static void main(String[] args) throws Exception{

        File file = new File("src/test/en/Medium_parents.txt");
        Map<String, Long> freq;
        try (Stream<String> words = Stream.of(new Scanner(file).next())) { // todo:
            freq = words
                    .collect(groupingBy(String::toLowerCase, counting()));
        }

        List<String> topTen = freq.keySet().stream()
                .sorted(comparing(freq::get).reversed())
                .limit(10)
                .collect(toList());

        System.out.println(topTen);
    }



}
