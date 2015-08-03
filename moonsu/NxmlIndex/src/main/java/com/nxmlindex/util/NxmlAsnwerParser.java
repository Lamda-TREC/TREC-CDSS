/**
 * 
 */
package com.nxmlindex.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.nxmlindex.common.vo.NxmlAnswer;
import com.opencsv.CSVReader;

/**
 * <pre>
 * com.nxmlindex.util
 *   |_ NxmlAsnwerParser.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 16. 오전 1:36:37
 * @Version : 
 */
public class NxmlAsnwerParser {
	
	
	
	private ArrayList<NxmlAnswer> nxmlAnswer = new ArrayList<NxmlAnswer>();
	
	
	public void nxmlAnswerParse() throws IOException{
		
		CSVReader reader = new CSVReader(new FileReader("2014answer.csv"));
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			
			
			System.out.println(nextLine[0]+","+nextLine[1]+","+nextLine[2]);
			
			NxmlAnswer nxml = new NxmlAnswer(nextLine[0], nextLine[1], nextLine[2]);
			
			nxmlAnswer.add(nxml);
			
		}
		
		
	}


	/**
	 * @return the nxmlAnswer
	 */
	public ArrayList<NxmlAnswer> getNxmlAnswer() {
		return nxmlAnswer;
	}


	/**
	 * @param nxmlAnswer the nxmlAnswer to set
	 */
	public void setNxmlAnswer(ArrayList<NxmlAnswer> nxmlAnswer) {
		this.nxmlAnswer = nxmlAnswer;
	}

}
