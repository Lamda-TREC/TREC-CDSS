/**
 * 
 */
package com.nxmlindex.nlptools;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * <pre>
 * com.nxmlindex.nlptools
 *   |_ CoreNLPTokenizer.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 12. 오후 10:20:21
 * @Version : 
 */
public class CoreNLPTokenizer {
	
	
	protected StanfordCoreNLP pipeline;

    public CoreNLPTokenizer() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        // StanfordCoreNLP loads a lot of models, so you probably
        // only want to do this once per execution
        this.pipeline = new StanfordCoreNLP(props);
    }
    
	
	public List<String> lemmatokenize(String documentText)
    {
        List<String> lemmas = new LinkedList<String>();

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);

        // run all Annotators on this text
        this.pipeline.annotate(document);

        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the list of lemmas
            	
            	if(token.get(PartOfSpeechAnnotation.class).startsWith("NN")||token.get(PartOfSpeechAnnotation.class).startsWith("VB"))
            	{
            		lemmas.add(token.get(LemmaAnnotation.class));
            	}
            		
            	
            }
        }

        return lemmas;
    }
	
	
	public String lemmatokenize_metamap(String documentText)
    {
        String lemma = "";

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);

        // run all Annotators on this text
        this.pipeline.annotate(document);

        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the list of lemmas
            	
            	if(token.get(PartOfSpeechAnnotation.class).startsWith("NN"))
            	{
            		lemma += token.get(LemmaAnnotation.class)+" ";
            	}
            		
            	
            }
        }

        return lemma;
    }
	
	
	public String lemma(String word)
    {
        String lemma = "";

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(word);

        // run all Annotators on this text
        this.pipeline.annotate(document);

        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the list of lemmas
            	
            	
            	lemma = token.get(LemmaAnnotation.class);
            	
            		
            	
            }
        }

        return lemma;
    }
	
	

	
	

}
