package org.tea.entity;

import org.tea.uima.types.RepeatedWord;

import java.util.ArrayList;
import java.util.List;

// todo: make it defensive
// todo: try to make it UIMA way (Eclipse plugin?)
public class Phrase {
    public final static String WORD_SEPARATOR = " ";

    private List<RepeatedWord> phraseWords = new ArrayList<>();
    //todo: try to optimize with TextStringBuilder?
    private StringBuilder text = new StringBuilder();
    private int begin;
    private int end;
    private String lcs;

    public Phrase() {
    }

    public Phrase(RepeatedWord word) {
        addWord(word);
    }

    public void addWord(RepeatedWord word) {
        if (this.phraseWords.isEmpty()) {
            begin = word.getBegin();
        }
        end = word.getEnd();
        this.phraseWords.add(word);
        if (text.length() != 0) {
            text.append(WORD_SEPARATOR);
        }
        text.append(word.getCoveredText());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phrase phrase1 = (Phrase) o;

        if (begin != phrase1.begin) return false;
        if (end != phrase1.end) return false;
        return text.toString().equals(phrase1.text.toString());
    }

    @Override
    public int hashCode() {
        int result = text.toString().hashCode();
        result = 31 * result + begin;
        result = 31 * result + end;
        return result;
    }

    public List<RepeatedWord> getPhraseWords() {
        return phraseWords;
    }

    public StringBuilder getText() {
        return text;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public String getLcs() {
        return lcs;
    }

    public void setLcs(String lcs) {
        this.lcs = lcs;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "text=" + text +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
