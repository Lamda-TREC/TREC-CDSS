package com.ndcg.evaluationNDCG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class MyRankLoader {

	public void init() {

		for (int i = 1; i < 31; i++) {
			HashMap<String, Double> topicanswer = new HashMap<String, Double>();
			info.MyRankHashMap.put(i, topicanswer);
		}
	}

	public void myRankLoader(String path,int rankNum) throws Exception {

		for (int index = 1; index < 31; index++) {
			BufferedReader br = new BufferedReader(new FileReader(new File(path+index+".csv")));
			
			String Line;
			Line = br.readLine();
			int num=1;
			while ((Line = br.readLine()) != null) {
				String nextLine[] = Line.split(",");
				int topic = Integer.parseInt(nextLine[0]);
				String pmcid = nextLine[2];
				double score = Double.parseDouble(nextLine[3]);

				info.MyRankHashMap.get(topic).put(pmcid.trim(), score);
				num++;
				if(rankNum==num)
				{
					break;
				}
				//System.out.println(topic);

			}
		}

	}
	
	public void myRankLoader3(String path,int rankNum) throws Exception {

		for (int index = 1; index < 31; index++) {
			BufferedReader br = new BufferedReader(new FileReader(new File(path+index+".csv")));
			
			String Line;
			Line = br.readLine();
			int num=1;
			while ((Line = br.readLine()) != null) {
				String nextLine[] = Line.split(",");
				String pmcid = nextLine[1];
				double score = Double.parseDouble(nextLine[2]);

				info.MyRankHashMap.get(index).put(pmcid.trim(), score);
				num++;
				if(rankNum==num)
				{
					break;
				}
				//System.out.println(topic);

			}
		}

	}

}
