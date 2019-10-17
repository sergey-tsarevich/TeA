package org.tea.fan;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;

public class TT {

    @Test
    public void sum() {
        List<String> words = new ArrayList<>();
        words.add("Hello!");
        words.sort(comparingInt(String::length));
        System.out.println(words);
    }

    @Test
    public void test() {
//        "Hello world!".chars().forEach(System.out::print);
//        "Hello world!".chars().forEach(c-> System.out.print((char) c));
        Stream.iterate(BigInteger.TEN, BigInteger::nextProbablePrime).forEach(System.out::println);
//        System.out.println(BigInteger.TEN.nextProbablePrime());
    }
}
