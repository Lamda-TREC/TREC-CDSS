package com.bordafuse.BordaFuse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BordaFuseCaluculator {

	public void calculateBordaFuse(int filesnum) throws IOException {
		
		int num = (int)Math.pow(2, filesnum -1) -1;
		for(int i = 0;i<num;i++)
		{

			HashMap<Integer, HashMap<String, Double>> resultHashMap = new HashMap<Integer, HashMap<String,Double>>();
			
			for (int index = 1; index < 31; index++) {
				HashMap<String, Double> result = new HashMap<String, Double>();
				resultHashMap.put(index, result);
			}
			
			info.resultBordaFuseHashMap.put(i, resultHashMap);
		}
		
		String path = "RUN06//DFR summary";
		
		for(int topic =11;topic<31;topic++)
		{
			comapretwo(0,1,topic,path+topic+".csv",0);
		}
		
/*
		path = "DFR_DFR_Weight//DFR summary";
		
		for(int topic =1;topic<31;topic++)
		{
			comapretwo(1,2,topic,path+topic+".csv",0);
		}
		
		path = "DFR_DFR_Weight//DFR summary";
		
		for(int topic =1;topic<31;topic++)
		{
			comapretwo(1,2,topic,path+topic+".csv",0);
		}
		
		
		path = "DFR_EDGE_DFR_Weight//DFR summary";
		
		for(int topic =1;topic<31;topic++)
		{
			comapretwo(1,2,topic,path+topic+".csv",0);
		}
		
		
		path = "DFR_DFR_EDGE_DFR_WEIGHT//DFR summary";
		
		for(int topic =1;topic<31;topic++)
		{
			comaprethree(0,1,2,topic,path+topic+".csv",0);
		}
*/
	}

	public void comapretwo(int first, int second,int topic,String path,int type) throws IOException {
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));

		HashMap<String, Integer> pmcidHashmap = new HashMap<String, Integer>();

		Iterator<String> it = info.FilesRankHashMap.get(first).get(topic).keySet()
				.iterator();

		while (it.hasNext()) {
			String pmcid = it.next();

			pmcidHashmap.put(pmcid, 1);
		}

		it = info.FilesRankHashMap.get(second).get(topic).keySet()
				.iterator();

		while (it.hasNext()) {
			String pmcid = it.next();

			pmcidHashmap.put(pmcid, 1);
		}
		
		
		it = pmcidHashmap.keySet().iterator();
		
		
		
		while(it.hasNext())
		{
			String pmcid = it.next();
			int rankA = 0;
			int rankB= 0;
			if(info.FilesRankHashMap.get(first).get(topic).containsKey(pmcid))
			{
				rankA = info.FilesRankHashMap.get(first).get(topic).get(pmcid);
			}else
			{
				rankA = info.FilesRankHashMap.get(first).get(topic).size()+1;
			}
			
			if(info.FilesRankHashMap.get(second).get(topic).containsKey(pmcid))
			{
				rankB = info.FilesRankHashMap.get(second).get(topic).get(pmcid);
				
			}else
			{
				rankB = info.FilesRankHashMap.get(second).get(topic).size()+1;
			}
			
			double score = 1.0 / (double)(rankA+rankB);
			
			info.resultBordaFuseHashMap.get(type).get(topic).put(pmcid, score);
								
			
		}
		
		
		
		it  = sortByValue(info.resultBordaFuseHashMap.get(type).get(topic)).iterator();
		
		bw.write("topicNum, rank, pmcid, score");
		bw.newLine();
		
		int num =1;
		while(it.hasNext())
		{
			String pmcid = it.next();
			double score = info.resultBordaFuseHashMap.get(type).get(topic).get(pmcid);
			
			bw.write(topic+", "+num+", "+pmcid+", "+score);
			bw.newLine();
			num++;
			if(num == 1001)
			{
				break;
			}
			
		}
		
		bw.flush();
		bw.close();				

	}
	
public void comaprethree(int first, int second,int three,int topic,String path,int type) throws IOException {
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));

		HashMap<String, Integer> pmcidHashmap = new HashMap<String, Integer>();

		Iterator<String> it = info.FilesRankHashMap.get(first).get(topic).keySet()
				.iterator();

		while (it.hasNext()) {
			String pmcid = it.next();

			pmcidHashmap.put(pmcid, 1);
		}

		it = info.FilesRankHashMap.get(second).get(topic).keySet()
				.iterator();

		while (it.hasNext()) {
			String pmcid = it.next();

			pmcidHashmap.put(pmcid, 1);
		}
		
		
		it = info.FilesRankHashMap.get(three).get(topic).keySet()
				.iterator();

		while (it.hasNext()) {
			String pmcid = it.next();

			pmcidHashmap.put(pmcid, 1);
		}
		
		it = pmcidHashmap.keySet().iterator();
		
		
		
		while(it.hasNext())
		{
			String pmcid = it.next();
			int rankA = 0;
			int rankB= 0;
			int rankC= 0;
			if(info.FilesRankHashMap.get(first).get(topic).containsKey(pmcid))
			{
				rankA = info.FilesRankHashMap.get(first).get(topic).get(pmcid);
			}else
			{
				rankA = info.FilesRankHashMap.get(first).get(topic).size()+1;
			}
			
			if(info.FilesRankHashMap.get(second).get(topic).containsKey(pmcid))
			{
				rankB = info.FilesRankHashMap.get(second).get(topic).get(pmcid);
				
			}else
			{
				rankB = info.FilesRankHashMap.get(second).get(topic).size()+1;
			}
			
			if(info.FilesRankHashMap.get(three).get(topic).containsKey(pmcid))
			{
				rankC = info.FilesRankHashMap.get(three).get(topic).get(pmcid);
				
			}else
			{
				rankC = info.FilesRankHashMap.get(three).get(topic).size()+1;
			}
			
			double score = 1.0 / (double)(rankA+rankB+rankC);
			
			info.resultBordaFuseHashMap.get(type).get(topic).put(pmcid, score);
								
			
		}
		
		
		
		it  = sortByValue(info.resultBordaFuseHashMap.get(type).get(topic)).iterator();
		
		bw.write("topicNum,rank,pmcid,score");
		bw.newLine();
		
		int num =1;
		while(it.hasNext())
		{
			String pmcid = it.next();
			double score = info.resultBordaFuseHashMap.get(type).get(topic).get(pmcid);
			
			bw.write(topic+","+num+","+pmcid+","+score);
			bw.newLine();
			num++;
			if(num == 1001)
			{
				break;
			}
			
		}
		
		bw.flush();
		bw.close();				

	}
	
	
	public List sortByValue(final Map map) {
		List<String> list = new ArrayList();
		list.addAll(map.keySet());

		Collections.sort(list, new Comparator() {

			public int compare(Object o1, Object o2) {
				Object v1 = map.get(o1);
				Object v2 = map.get(o2);

				return ((Comparable) v1).compareTo(v2);
			}

		});
		Collections.reverse(list); // �ּ��� ��������
		return list;
	}

}
