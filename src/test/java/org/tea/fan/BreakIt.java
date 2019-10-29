package org.tea.fan;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;
import java.util.Locale;

public class BreakIt {
    public static void main(String args[]) throws IOException {
//        String text = FileUtils.readFileToString(new File("src/test/en/Small_geeks.txt"), StandardCharsets.UTF_8);
        String text = FileUtils.readFileToString(new File("src/test/en/Medium_parents.txt"), StandardCharsets.UTF_8);

        String stringToExamine = text;
        //print each word in order
//        BreakIterator boundary = BreakIterator.getWordInstance(Locale.US);
        BreakIterator boundary = BreakIterator.getSentenceInstance();
        boundary.setText(stringToExamine);
        printEachForward(boundary, stringToExamine);
       /* //print each sentence in reverse order
        boundary = BreakIterator.getSentenceInstance(Locale.US);
        boundary.setText(stringToExamine);
        printEachBackward(boundary, stringToExamine);
        printFirst(boundary, stringToExamine);
        printLast(boundary, stringToExamine);*/
    }

    //    Print each element in order:
    public static void printEachForward(BreakIterator boundary, String source) {
        int start = boundary.first();
        for (int end = boundary.next();
             end != BreakIterator.DONE;
             start = end, end = boundary.next()) {

            String substring = source.substring(start, end);
            boolean isWhitespace = true;
            for (int i = 0, n = substring.length(); i < n; i++) {
                if (!Character.isWhitespace(substring.charAt(i))) {
                    isWhitespace = false;
                    break;
                }
            }
            if(!isWhitespace) System.out.println("\"" + substring + "\"");
        }
    }

    //    Print each element in reverse order:
    public static void printEachBackward(BreakIterator boundary, String source) {
        int end = boundary.last();
        for (int start = boundary.previous();
             start != BreakIterator.DONE;
             end = start, start = boundary.previous()) {
            System.out.println(source.substring(start, end));
        }
    }

    //    Print first element:
    public static void printFirst(BreakIterator boundary, String source) {
        int start = boundary.first();
        int end = boundary.next();
        System.out.println(source.substring(start, end));
    }

    //    Print last element:
    public static void printLast(BreakIterator boundary, String source) {
        int end = boundary.last();
        int start = boundary.previous();
        System.out.println(source.substring(start, end));
    }

    //    Print the element at a specified position:
    public static void printAt(BreakIterator boundary, int pos, String source) {
        int end = boundary.following(pos);
        int start = boundary.previous();
        System.out.println(source.substring(start, end));
    }

    //    Find the next word:
    public static int nextWordStartAfter(int pos, String text) {
        BreakIterator wb = BreakIterator.getWordInstance();
        wb.setText(text);
        int last = wb.following(pos);
        int current = wb.next();
        while (current != BreakIterator.DONE) {
            for (int p = last; p < current; p++) {
                if (Character.isLetter(text.codePointAt(p)))
                    return last;
            }
            last = current;
            current = wb.next();
        }
        return BreakIterator.DONE;
    }
}
