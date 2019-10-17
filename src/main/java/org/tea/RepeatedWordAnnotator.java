package org.tea;

import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.tea.uima.types.RepeatedWord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@TypeCapability(outputs = {"org.tea.uima.types.RepeatedWord"})
public class RepeatedWordAnnotator
        extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {
    private Pattern sameWordPattern = Pattern.compile("(?s)\\b(\\w+(?:\\s*\\w*))\\s+.*?\\1");

    public static final String PARAM_STRING = "stringParam";
    @ConfigurationParameter(name = PARAM_STRING)
    private String stringParam;

    @Override
    public void process(JCas jCas) {
        System.out.println("Hello world!  Say 'hi' to " + stringParam);
        // get document text
        String docText = jCas.getDocumentText();
        // search for Yorktown room numbers
        Matcher matcher = sameWordPattern.matcher(docText);
        while (matcher.find()) {
            // found one - create annotation
            RepeatedWord annotation = new RepeatedWord(jCas, matcher.start(), matcher.end());
            annotation.addToIndexes();
        }

    }
}
