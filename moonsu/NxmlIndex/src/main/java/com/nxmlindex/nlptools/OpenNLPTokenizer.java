/**
 * 
 */
package com.nxmlindex.nlptools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * <pre>
 * com.nxmlindex.tokenizer
 *   |_ OpenNLPTokenizer.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 5. 오전 11:02:53
 * @Version : 
 */
public class OpenNLPTokenizer {
	
	
	private final String ENGLISTH_TOKEN = "en-token.bin";
	private TokenizerModel model =null;
	
	public void initOpenNLP() throws FileNotFoundException{
		
		InputStream modelIn = new FileInputStream(ENGLISTH_TOKEN);
		try {
		  model = new TokenizerModel(modelIn);
		  
		  
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
		
	}
	
	
	public String[] tokenizeText(String text){
		
		String result [] = null;
		if(text == null)
			text ="";
		
		Tokenizer tokenizer = new TokenizerME(model);
		result= tokenizer.tokenize(text);
		
		return result;
		 
	}
	

}
