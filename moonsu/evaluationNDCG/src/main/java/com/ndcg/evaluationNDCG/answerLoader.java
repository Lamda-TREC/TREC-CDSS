package com.ndcg.evaluationNDCG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class answerLoader {

	
	
	public void init(){
		
		for(int i=1; i< 31; i++)
		{
			HashMap<String, Integer> topicanswer = new HashMap<String, Integer>();
			info.AnswerHashMap.put(i, topicanswer);
		}
		
	}
	
	public void loadAnswer() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(new File("qrels2014 (2).csv")));
		
		
	     String Line;
	     try {
			while((Line = br.readLine())!=null) {
				
				 String [] nextLine = Line.split(",");
				// System.out.println(i);
				 int topic = Integer.parseInt(nextLine[0]);
				 String pmcid = nextLine[2];
				 //System.out.println(pmcid.length());
				 int relevance = Integer.parseInt(nextLine[3]);
				 
				 info.AnswerHashMap.get(topic).put(pmcid.trim(), relevance);
				 //System.out.println(topic);
				 
				 
			 }
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
