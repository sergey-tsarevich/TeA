package org.tea;


import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.ProcessTrace;
import org.tea.uima.types.RepeatedWord;

import java.util.Collection;

import static org.apache.uima.fit.factory.JCasFactory.createJCas;
import static org.apache.uima.fit.util.JCasUtil.select;

public class SimpleEngine {


    static String text = "Welcome to the world of Geeks \n" +
            "This portal has been created to provide well written well thought and well explained \n" +
            "solutions for selected questions If you like Geeks for Geeks and would like to contribute \n" +
            "here is your chance You can write article and mail your article to contribute at \n" +
            "geeksforgeeks org See your article appearing on the Geeks for Geeks main page and help \n" +
            "thousands of other Geeks";

    public static void main(String[] args) throws Exception {
        // todo: choose from WordFrequencyFromTextTest!

        JCas jCas = createJCas();
        jCas.setDocumentText(text);

/*
        runPipeline(jCas,
                createEngineDescription(MyTokenizer.class),
                createEngineDescription(MyTagger.class));

        for(Token token : iterate(jCas, Token.class)){
            System.out.println(token.getTag());
        }
*/
        AnalysisEngine analysisEngine = AnalysisEngineFactory.createEngine(
                RepeatedWordAnnotator.class,
                RepeatedWordAnnotator.PARAM_STRING, "uimaFIT");

        ProcessTrace process = analysisEngine.process(jCas);
        System.out.println("Analysis duration in ms: " + process.getEvent(RepeatedWordAnnotator.class.getName(), "Analysis").getDuration());

        Collection<RepeatedWord> select = select(jCas, RepeatedWord.class);
        for (RepeatedWord repeatedWord : select) {
            System.out.println(repeatedWord.getCoveredText());
        }

    }
}


