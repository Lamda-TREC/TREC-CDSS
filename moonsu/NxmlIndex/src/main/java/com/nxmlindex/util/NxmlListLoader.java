/**
 * 
 */
package com.nxmlindex.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections.map.HashedMap;

/**
 * <pre>
 * com.nxmlindex.util
 *   |_ ReadNxmlList.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 5. 오전 11:23:10
 * @Version : 
 */
public class NxmlListLoader {
	
	
	private final String NXML_LIST = "2014answerNxmlList.txt";
	private ArrayList<String> nxmlList = null;
	private HashMap<Integer, String> nxmlAnswerHashMap = null;
	
	public void loadNxmlList() throws IOException{
		
		nxmlList = new ArrayList<String>();
		nxmlAnswerHashMap = new HashMap<Integer, String>();
		
		InputStream is =new FileInputStream(NXML_LIST);
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		String row=null;
		int num =0;
		while ((row = bf.readLine()) != null) {
			
			if(num ==0 )
				row = row.substring(1);
			
			nxmlList.add(row);
			nxmlAnswerHashMap.put(num, row);
			num++;
		}

		
	}

	/**
	 * @return the nxmlList
	 */
	public ArrayList<String> getNxmlList() {
		return nxmlList;
	}

	
	/**
	 * @return the nxmlAnswerHashMap
	 */
	public HashMap<Integer, String> getNxmlAnswerHashMap() {
		return nxmlAnswerHashMap;
	}

	
	
	

}
