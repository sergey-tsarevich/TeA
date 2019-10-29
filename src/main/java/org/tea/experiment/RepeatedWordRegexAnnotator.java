package org.tea.experiment;

import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.tea.uima.types.RepeatedWord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegEx limitation: max 2-words of the nearest phrases and ignoring intersections.
 */
@TypeCapability(outputs = {"org.tea.uima.types.RepeatedWord"})
public class RepeatedWordRegexAnnotator
        extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {
    private Pattern sameWordPattern = Pattern.compile("(?s)\\b((?:\\w+\\s+){2,}).*?(\\1)");

    public static final String PARAM_STRING = "stringParam";
    @ConfigurationParameter(name = PARAM_STRING)
    private String stringParam;

    @Override
    public void process(JCas jCas) {
        String docText = jCas.getDocumentText();
        Matcher matcher = sameWordPattern.matcher(docText);
        while (matcher.find()) {
            RepeatedWord firstOccurence = new RepeatedWord(jCas, matcher.start(1), matcher.end(1));
            firstOccurence.addToIndexes();
            RepeatedWord secondOccurence = new RepeatedWord(jCas, matcher.start(2), matcher.end(2));
            secondOccurence.addToIndexes();
        }
    }
}
