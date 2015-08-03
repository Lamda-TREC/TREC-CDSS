package com.nxmlindex.util;

import java.util.HashMap;
import java.util.Iterator;

import com.nxmlindex.NxmlIndexApplication;
import com.nxmlindex.NxmlIndexApplication;

public class TFIDF {

	public void calculateTFIDF() {

		Iterator<String> keys = NxmlIndexApplication.DiaTFHashMap.keySet()
				.iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			double tf = NxmlIndexApplication.DiaTFHashMap.get(key);
			
			double idf = NxmlIndexApplication.IDFHashMap.get(key);
			
			double tfidf = tf*idf;
			
			NxmlIndexApplication.DiaTFIDFHashMap.put(key, tfidf);
			
			HashMap<Integer, Double> value = new HashMap<Integer, Double>();
			
			value.put(0, tfidf);
			
			NxmlIndexApplication.TotalTFIDFHashMap.put(key, value);
		
		}
		
		keys = NxmlIndexApplication.TestTFHashMap.keySet()
				.iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			double tf = NxmlIndexApplication.TestTFHashMap.get(key);
			
			double idf = NxmlIndexApplication.IDFHashMap.get(key);
			
			double tfidf = tf*idf;
			
			NxmlIndexApplication.TestTFIDFHashMap.put(key, tfidf);
			
			if(NxmlIndexApplication.TotalTFIDFHashMap.containsKey(key))
			{
				HashMap<Integer, Double> value = NxmlIndexApplication.TotalTFIDFHashMap.get(key);
				
				value.put(1, tfidf);
				
				NxmlIndexApplication.TotalTFIDFHashMap.put(key, value);
			}
			else
			{
				HashMap<Integer, Double> value = new HashMap<Integer, Double>();
				
				value.put(1, tfidf);
				
				NxmlIndexApplication.TotalTFIDFHashMap.put(key, value);
			}
		
		}
		
		keys = NxmlIndexApplication.TreatTFHashMap.keySet()
				.iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			double tf = NxmlIndexApplication.TreatTFHashMap.get(key);
			
			double idf = NxmlIndexApplication.IDFHashMap.get(key);
			
			double tfidf = tf*idf;
			
			NxmlIndexApplication.TreatTFIDFHashMap.put(key, tfidf);
		
			if(NxmlIndexApplication.TotalTFIDFHashMap.containsKey(key))
			{
				HashMap<Integer, Double> value = NxmlIndexApplication.TotalTFIDFHashMap.get(key);
				
				value.put(2, tfidf);
				
				NxmlIndexApplication.TotalTFIDFHashMap.put(key, value);
			}
			else
			{
				HashMap<Integer, Double> value = new HashMap<Integer, Double>();
				
				value.put(2, tfidf);
				
				NxmlIndexApplication.TotalTFIDFHashMap.put(key, value);
			}
		}

	}

	public void calculateIDF() {

		Iterator<String> keys = NxmlIndexApplication.DiaHashMap.keySet()
				.iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			int count = NxmlIndexApplication.DiaHashMap.get(key);

			NxmlIndexApplication.DFHashMap.put(key, 1);
		}

		keys = NxmlIndexApplication.TestHashMap.keySet().iterator();

		while (keys.hasNext()) {
			String key = keys.next();
			int count = NxmlIndexApplication.TestHashMap.get(key);
			if (!NxmlIndexApplication.DFHashMap.containsKey(key)) {
				NxmlIndexApplication.DFHashMap.put(key, 1);
			} else {
				NxmlIndexApplication.DFHashMap.put(key, 2);
			}
		}

		keys = NxmlIndexApplication.TreatHashMap.keySet().iterator();

		while (keys.hasNext()) {
			String key = keys.next();
			int count = NxmlIndexApplication.TreatHashMap.get(key);
			if (!NxmlIndexApplication.DFHashMap.containsKey(key)) {
				NxmlIndexApplication.DFHashMap.put(key, 1);
			} else {
				count = NxmlIndexApplication.DFHashMap.get(key);

				count++;

				NxmlIndexApplication.DFHashMap.put(key, count);
			}
		}

		keys = NxmlIndexApplication.DFHashMap.keySet().iterator();

		while (keys.hasNext()) {
			String key = keys.next();
			double df = (double)NxmlIndexApplication.DFHashMap.get(key);

			double idf = Math.log(1.0+3.0/ df);

			NxmlIndexApplication.IDFHashMap.put(key, idf);

		}

	}
	
	
	public void calculateTF(){
		
		Iterator<String> keys = NxmlIndexApplication.DiaHashMap.keySet()
				.iterator();
		double total = 0;
		while (keys.hasNext()) {
			String key = keys.next();
			int count = NxmlIndexApplication.DiaHashMap.get(key);
			total+=count;
		}
		
		keys = NxmlIndexApplication.DiaHashMap.keySet()
				.iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			int count = NxmlIndexApplication.DiaHashMap.get(key);

			double tf = (double)count / (double)total;
			
			NxmlIndexApplication.DiaTFHashMap.put(key, tf);
			
			
		}
		
		keys = NxmlIndexApplication.TestHashMap.keySet()
				.iterator();
		total = 0;
		while (keys.hasNext()) {
			String key = keys.next();
			int count = NxmlIndexApplication.TestHashMap.get(key);
			total+=count;
		}
		
		keys = NxmlIndexApplication.TestHashMap.keySet()
				.iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			int count = NxmlIndexApplication.TestHashMap.get(key);
			
			double tf = (double)count / (double)total;
			
			NxmlIndexApplication.TestTFHashMap.put(key, tf);
			
			
		}
		
		keys = NxmlIndexApplication.TreatHashMap.keySet()
				.iterator();
		total = 0;
		while (keys.hasNext()) {
			String key = keys.next();
			int count = NxmlIndexApplication.TreatHashMap.get(key);
			total+=count;
		}
		
		keys = NxmlIndexApplication.TreatHashMap.keySet()
				.iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			int count = NxmlIndexApplication.TreatHashMap.get(key);

			double tf = (double)count / (double)total;
			
			NxmlIndexApplication.TreatTFHashMap.put(key, tf);
			
			
		}
		
		
		
	}

	
	public void printTF(){
		
		Iterator<String> keys = NxmlIndexApplication.DiaTFHashMap.keySet()
				.iterator();
	
		while (keys.hasNext()) {
			String key = keys.next();
			double tf = NxmlIndexApplication.DiaTFHashMap.get(key);
			System.out.println(key+" : "+tf);
		}
		
		keys = NxmlIndexApplication.TestTFHashMap.keySet()
				.iterator();
	
		while (keys.hasNext()) {
			String key = keys.next();
			double tf = NxmlIndexApplication.TestTFHashMap.get(key);
			System.out.println(key+" : "+tf);
		}
		
		keys = NxmlIndexApplication.TreatTFHashMap.keySet()
				.iterator();
	
		while (keys.hasNext()) {
			String key = keys.next();
			double tf = NxmlIndexApplication.TreatTFHashMap.get(key);
			System.out.println(key+" : "+tf);
		}
		
	}
	
	public void printIDF(){
		
		Iterator<String> keys = NxmlIndexApplication.IDFHashMap.keySet()
				.iterator();
	
		while (keys.hasNext()) {
			String key = keys.next();
			double idf = NxmlIndexApplication.IDFHashMap.get(key);
			System.out.println(key+" : "+idf);
		}
		
		
	}
	
}
