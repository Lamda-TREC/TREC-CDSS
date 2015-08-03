package com.IndexServer.CoreNlp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.UrlValidator;

import com.IndexServer.socketServer.Info;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;


public class StanfordLemmatizer {
	
	protected StanfordCoreNLP pipeline;

    public StanfordLemmatizer() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        // StanfordCoreNLP loads a lot of models, so you probably
        // only want to do this once per execution
        this.pipeline = new StanfordCoreNLP(props);
    }
    
    
    public void EnglishMorhemeContent(String category,String id) {

		String input = Info.CONTENT_FOLDER_PATH + category + "/text/"
				+ id + ".txt";
		String output = Info.CONTENT_FOLDER_PATH + category + "/text/"
				+ id + "_output.txt";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(input)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(output));
			bw.write("DOC	X	");
			String str = null;
			while ((str = br.readLine()) != null) {

				
			      
				List<String> result = lemmatokenize(str);
				
				for(String word : result)
				{
					if(!isCheckEmailCorrect(word.toLowerCase()) && !isUrlValidator(word))
					{
						String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
						word =word.replaceAll(match, "");
						bw.write(word.toLowerCase()+" ");
					}
				}
			}
			bw.write("\n");
			bw.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
    public boolean isCheckEmailCorrect(String email) {
        if(email.length() == 0) {
            return false;
        }
        email.replaceAll(" ", "").trim();
     
        String pttn = "^\\D.+@.+\\.[a-z]+";
        Pattern p = Pattern.compile(pttn);
        Matcher m = p.matcher(email);
     
        if(m.matches()) {
            return true;
        }
     
        return false;
    }
    
    
    public boolean isUrlValidator(String url)
    {

    	UrlValidator urlValidator = new UrlValidator();
    	
    	String newUrl="";
    	if(!url.contains("http")){
    		newUrl+="http://"+url;
    	}else
    	{
    		newUrl = url;
    	}
    	
	    //valid URL
	    if (urlValidator.isValid(newUrl)) {
	       return true;
	    }else
	    {
	    	return false;
	    }
    }
    
    
    public ArrayList<String> EnglishMorhemeContentInfo(String title, String Summary) {
		
		ArrayList<String> result = new ArrayList<String>();
		String str = title + " " + Summary;
		
		List<String> token = lemmatokenize(str);
		
		for(String word : token)
		{
			if(!isCheckEmailCorrect(word) && !isUrlValidator(word))
			{
				String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
				word =word.replaceAll(match, "");
				result.add(word.toLowerCase());
			}
			
		}
		
		return result;

	}

    
    public ArrayList<String> keywordMorpheme(String keyword){
    	ArrayList<String> result = new ArrayList<String>();
		String str = keyword;
		
		List<String> token = lemmatokenize(str);
		
		for(String word : token)
		{
			
				String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
				word =word.replaceAll(match, "");
				result.add(word.toLowerCase());
			
		}
		
		return result;
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
            	
            	if(token.get(PartOfSpeechAnnotation.class).startsWith("NN"))
            	{
            		lemmas.add(token.get(LemmaAnnotation.class));
            	}
            		
            	
            }
        }

        return lemmas;
    }

}
