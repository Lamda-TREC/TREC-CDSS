/**
 * 
 */
package com.nxmlindex.nlptools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * <pre>
 * com.nxmlindex.tokenizer
 *   |_ TextPreprocess.java
 * </pre>
 * 
 * Desc :
 * 
 * @Company : LAMDA in Ajou Univ
 * @Author : ChaMunsu
 * @Date : 2015. 7. 5. 오전 11:09:30
 * @Version :
 */
public class TextPreprocess {

	private final String ENGLISH_STOPWORD_GOOGLE = "en.txt";
	private ArrayList<String> stopword=null;

	public void loadStopword() throws IOException {

		stopword = new ArrayList<String>();
		
		InputStream is = new FileInputStream(ENGLISH_STOPWORD_GOOGLE);
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		String row=null;
				
		while ((row = bf.readLine()) != null) {
			
			stopword.add(row);
		}

	}

	
	/**
	 * @return the stopword
	 */
	public ArrayList<String> getStopword() {
		return stopword;
	}

	
	
	
}
