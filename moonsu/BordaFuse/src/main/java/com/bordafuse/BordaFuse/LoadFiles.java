package com.bordafuse.BordaFuse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;



public class LoadFiles {

	private int filenum = 0;
	private String[] filepath;

	public void init(int filenum, String[] filepath) {

		this.filenum = filenum;
		this.filepath = filepath;

	}

	public void filesLoader(int rankNum) throws Exception {

		for (int fileindex = 0; fileindex < filenum; fileindex++) {
			
			HashMap<Integer, HashMap<String, Integer>> MyRankHashMap = new HashMap<Integer, HashMap<String,Integer>>();

			for (int index = 11; index < 31; index++) {
				HashMap<String, Integer> topicanswer = new HashMap<String, Integer>();

				MyRankHashMap.put(index, topicanswer);
				
				BufferedReader br = new BufferedReader(new FileReader(
						new File(filepath[fileindex]+index+".csv")));

				String Line;
				Line = br.readLine();
				int num = 1;
				while ((Line = br.readLine()) != null) {
					String nextLine[] = Line.split(",");
					int topic = Integer.parseInt(nextLine[0]);
					String pmcid = nextLine[2];
					int rank = Integer.parseInt(nextLine[1].trim());
					MyRankHashMap.get(topic).put(pmcid.trim(), rank);
					num++;
					if (rankNum == num) {
						break;
					}
					// System.out.println(topic);

				}
				

			}
			
			info.FilesRankHashMap.put(fileindex, MyRankHashMap);
		}

	}

}
