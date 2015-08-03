package com.ndcg.evaluationNDCG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NDCGEvaluator {

	public void calculateINDCG(int rankNum) {

		for (int topic = 1; topic < 31; topic++) {
			Iterator<String> it = sortByValue(info.AnswerHashMap.get(topic))
					.iterator();

			int rank = 1;
			double idcg = 0.0;
			while (it.hasNext()) {
				String pmcid = it.next();

				int relevance = info.AnswerHashMap.get(topic).get(pmcid);
				/*if(rank !=1)
				{
					idcg+= ((double)relevance)/(Math.log(rank)/Math.log(2));
					
				}else
				{
					idcg +=(double)relevance;
				}*/
				
				idcg+= (Math.pow(2, relevance) - 1)/(Math.log(rank+1)/Math.log(2));
				
				if(rankNum ==rank)
				{
					break;
				}
				rank++;

				
			}
			rank = 1;
			info.TopicIDCG.put(topic, idcg);
			
		}

	}
	
	
	public void calculateDCG(int rankNum){
		
		
		for (int topic = 1; topic < 31; topic++) {
			Iterator<String> it = sortByValue(info.MyRankHashMap.get(topic))
					.iterator();

			int rank = 1;
			double dcg = 0.0;
			while (it.hasNext()) {
				String pmcid = it.next();
				//System.out.println(pmcid);
				int relevance = 0;
				if(info.AnswerHashMap.get(topic).containsKey(pmcid))
				{
					//System.out.println(pmcid);
					relevance = info.AnswerHashMap.get(topic).get(pmcid);
					
				}else
				{
					relevance = 0;
				}
				
			/*	if(rank !=1)
				{
					dcg+=  ((double)relevance)/(Math.log(rank)/Math.log(2));
					
				}else
				{
					dcg +=(double)relevance;
				}*/
				
				dcg+= (Math.pow(2, relevance) - 1)/(Math.log(rank+1)/Math.log(2));
				if(rankNum ==rank)
				{
					break;
				}
				rank++;
				
			}

			rank = 1;
			info.TopicDCG.put(topic, dcg);
			
		}
		
		
	}
	
	public void calculatNDCG(){
		
		double sum = 0.0;
		for(int topic =1; topic <31; topic++)
		{
			double ndcg = info.TopicDCG.get(topic) / info.TopicIDCG.get(topic);
			
			info.TopicNDCG.put(topic, ndcg);
			sum+=ndcg;
		}
		
		info.AverageNDCG = sum/30.0;
		
		
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
