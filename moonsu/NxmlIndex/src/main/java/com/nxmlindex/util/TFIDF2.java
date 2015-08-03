/**
 * 
 */
package com.nxmlindex.util;

import java.util.HashMap;
import java.util.Iterator;

import com.nxmlindex.NxmlIndexApplication;

/**
 * <pre>
 * com.nxmlindex.util
 *   |_ TFIDF2.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 16. 오전 11:15:45
 * @Version : 
 */
public class TFIDF2 {
	
	

	public void calculateTFIDF(){
		
		Iterator<String> keys = NxmlIndexApplication.DocumentTFHashMap.keySet().iterator();

		while (keys.hasNext()) {
			
			String key = keys.next();
			
			HashMap<String, Double> wordTFHashMap = NxmlIndexApplication.DocumentTFHashMap.get(key);
			
			Iterator<String> tfKeys = wordTFHashMap.keySet().iterator();
			
			while (tfKeys.hasNext()) {
			
				String word = tfKeys.next();
				
				double tf = wordTFHashMap.get(word);
				
				double idf = NxmlIndexApplication.IDFHashMap.get(word);
				
				double tfidf = tf*idf;
				
				NxmlIndexApplication.TotalDocumentTFIDFHashMap.get(key).put(word, tfidf);
				
			}
			
		}
		
		
		
		
	}
	
	
	public void averageTFIDF(){
		
		Iterator<String> keys = NxmlIndexApplication.TotalDocumentTFIDFHashMap.keySet().iterator();

		while (keys.hasNext()) {
			
			String pmcid = keys.next();
			
			HashMap<String, Double> metaMapTFDIF = NxmlIndexApplication.TotalDocumentTFIDFHashMap.get(pmcid);
			
			
			Iterator<String> keysmeta = metaMapTFDIF.keySet().iterator();
			
			while(keysmeta.hasNext())
			{
				String word = keysmeta.next();
				
				double tfidf = metaMapTFDIF.get(word);
				
				if(NxmlIndexApplication.AverageTotalDocumentTFIDFHashMap.containsKey(word))
				{
					double sumtfdif = NxmlIndexApplication.AverageTotalDocumentTFIDFHashMap.get(word);
					
					sumtfdif +=tfidf;

					NxmlIndexApplication.AverageTotalDocumentTFIDFHashMap.put(word, sumtfdif);
					
				}
				else
				{
					NxmlIndexApplication.AverageTotalDocumentTFIDFHashMap.put(word, tfidf);
				}
			}
		}
		
		keys = NxmlIndexApplication.AverageTotalDocumentTFIDFHashMap.keySet().iterator();
		double size = (double)NxmlIndexApplication.TotalDocumentTFIDFHashMap.size();
		
		while(keys.hasNext())
		{
			String key = keys.next();
			
			
			double tfidf = NxmlIndexApplication.AverageTotalDocumentTFIDFHashMap.get(key);
			
			double avergtfdif = tfidf/size;
			
			NxmlIndexApplication.AverageTotalDocumentTFIDFHashMap.put(key, avergtfdif);
		}
		
		
	}
	
	public void calculateIDF(){
		
		
		
			Iterator<String> keys = NxmlIndexApplication.DocumentHashMap.keySet().iterator();
			
			while(keys.hasNext())
			{
				String key = keys.next();
				
				HashMap<String, Integer> document = NxmlIndexApplication.DocumentHashMap.get(key);
				
				Iterator<String> keysdoc = document.keySet().iterator();
				
				while(keysdoc.hasNext())
				{
					String word = keysdoc.next();
					
					if(NxmlIndexApplication.DFHashMap.containsKey(word))
					{
						int count = NxmlIndexApplication.DFHashMap.get(word);
						count++;
						NxmlIndexApplication.DFHashMap.put(word, count);
						
					}else
					{
						NxmlIndexApplication.DFHashMap.put(word, 1);
					}
				}
				
			}
		
		
		
		keys = NxmlIndexApplication.DFHashMap.keySet().iterator();
		
		int size = NxmlIndexApplication.DocumentHashMap.size();

		while (keys.hasNext()) {
			String key = keys.next();
			double df = (double)NxmlIndexApplication.DFHashMap.get(key);

			double idf = Math.log(1.0+size/ df);

			NxmlIndexApplication.IDFHashMap.put(key, idf);

		}
	}
	
	
	
	public void calculateTF(){
		
		Iterator<String> keys = NxmlIndexApplication.DocumentHashMap.keySet().iterator();
		
		while(keys.hasNext())
		{
			String key = keys.next();
			
			HashMap<String, Integer> wordHashMap = NxmlIndexApplication.DocumentHashMap.get(key);
			
			Iterator<String> wordKeys = wordHashMap.keySet().iterator();
			double total = 0;
			while (wordKeys.hasNext()) {
				String word = wordKeys.next();
				int count = wordHashMap.get(word);
				total+=count;
			}
			
			wordKeys = wordHashMap.keySet()
					.iterator();
			while (wordKeys.hasNext()) {
				String word = wordKeys.next();
				int count = wordHashMap.get(word);

				double tf = (double)count / (double)total;
				
				NxmlIndexApplication.DocumentTFHashMap.get(key).put(word, tf);
				
				
			}
			
			
		}
		
	}

}
