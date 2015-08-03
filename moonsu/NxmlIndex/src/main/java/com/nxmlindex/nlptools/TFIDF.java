/**
 * 
 */
package com.nxmlindex.nlptools;

import java.util.HashMap;



/**
 * <pre>
 * com.nxmlindex.nlptools
 *   |_ TFIDF.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 5. 오후 12:02:46
 * @Version : 
 */
public class TFIDF {
	
	
	private HashMap<String, Integer> IDF = new HashMap<String, Integer>(); 
	
	
	public void addIDFWord(String word){
		
		
		if(IDF.containsKey(word))
		{
			int count = IDF.get(word);
			count += 1;
			IDF.put(word, count);
			
		}else
		{
			IDF.put(word, 1);
		}
		
	}


	/**
	 * @return the iDF
	 */
	public HashMap<String, Integer> getIDF() {
		return IDF;
	}



	
	

}
