/**
 * 
 */
package com.nxmlindex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.nxmlindex.util.NxmlListLoader;

/**
 * <pre>
 * com.nxmlindex.service
 *   |_ MetaMapAnalysisService.java
 * </pre>
 * 
 * Desc :
 * 
 * @Company : LAMDA in Ajou Univ
 * @Author : ChaMunsu
 * @Date : 2015. 7. 11. 오후 1:42:29
 * @Version :
 */
public class MetaMapAnalysisService {

	
	private NxmlListLoader nxmlListLoader = new NxmlListLoader();
	private HashMap<Integer, String> answerNxml = null;

	
	public void loadNxmlList() {

		try {
			nxmlListLoader.loadNxmlList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		answerNxml = nxmlListLoader
				.getNxmlAnswerHashMap();

	}
	
	
	
	
	
	
	
	
}
