package com.ndcg.evaluationNDCG;

import java.util.HashMap;

public class info {

	
	public static HashMap<Integer, HashMap<String, Integer>> AnswerHashMap = new HashMap<Integer, HashMap<String,Integer>>();
	
	public static HashMap<Integer, HashMap<String, Double>> MyRankHashMap = new HashMap<Integer, HashMap<String,Double>>();
		
	public static HashMap<Integer, Double> TopicIDCG = new HashMap<Integer, Double>();
	
	public static HashMap<Integer, Double> TopicDCG = new HashMap<Integer, Double>();
	
	public static HashMap<Integer, Double> TopicNDCG = new HashMap<Integer, Double>();
	
	public static double AverageNDCG=0.0;
}
