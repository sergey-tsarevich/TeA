package org.tea;


import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.u_compare.shared.syntactic.Token;

import static java.util.stream.IntStream.iterate;
import static org.apache.uima.fit.factory.JCasFactory.createJCas;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

public class SimpleEngine {


    static String text = "Welcome to the world of Geeks \n" +
            "This portal has been created to provide well written well thought and well explained \n" +
            "solutions for selected questions If you like Geeks for Geeks and would like to contribute \n" +
            "here is your chance You can write article and mail your article to contribute at \n" +
            "geeksforgeeks org See your article appearing on the Geeks for Geeks main page and help \n" +
            "thousands of other Geeks";

    public static void main(String[] args) throws UIMAException {
        // todo: choose from WordFrequencyFromTextTest!

        JCas jCas = createJCas();

        jCas.setDocumentText("some text");
/*

        runPipeline(jCas,
                createEngineDescription(MyTokenizer.class),
                createEngineDescription(MyTagger.class));

        for(Token token : iterate(jCas, Token.class)){
            System.out.println(token.getTag());
        }
*/






    }
}


