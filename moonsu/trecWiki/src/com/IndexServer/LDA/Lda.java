package com.IndexServer.LDA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.IndexServer.socketServer.Info;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.CharSequenceLowercase;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.Alphabet;
import cc.mallet.types.IDSorter;
import cc.mallet.types.InstanceList;

public class Lda {

	private ArrayList<topicdist> topicarray = new ArrayList<topicdist>();
	private double sorted_size = 0.0;
	private HashMap<String, Double> totalword = new HashMap<>();
	private HashMap<String, Double> rank = new HashMap<>();
	private String path;
	private String savepath;
	


	public HashMap<Integer, String> extractorWords(String category,String id) throws IOException {

		ArrayList<String> topWord =null;
		HashMap<Integer, String> topWord2 = null;
		path = Info.CONTENT_FOLDER_PATH+category+"/text/"+id+ "_output.txt";
		
		savepath = Info.CONTENT_FOLDER_PATH+category+"/text/"+id+ "LDA_output.txt";
		
		ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

		pipeList.add( new CharSequenceLowercase() );
		pipeList.add( new CharSequence2TokenSequence(Pattern.compile("(\\w+\\s*\\w+)(,\\s*\\d+)*")) );
		pipeList.add( new TokenSequenceRemoveStopwords(new File("en.txt"), "UTF-8", false, false, false) );
		pipeList.add( new TokenSequence2FeatureSequence() );

		InstanceList instances = new InstanceList (new SerialPipes(pipeList));

		System.out.println(path);
		Reader fileReader = new InputStreamReader(new FileInputStream(new File(path)), "UTF-8");
		instances.addThruPipe(new CsvIterator (fileReader, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"),
											   3, 2, 1)); // data, label, name fields

		
		
	
		ParallelTopicModel model = new ParallelTopicModel(
				Topic_Parameters.LDA_TOPIC_NUM, Topic_Parameters.LDA_ALPHA
						* Topic_Parameters.LDA_TOPIC_NUM,
				Topic_Parameters.LDA_BETA);

		model.addInstances(instances);
		model.setNumThreads(4);
		model.setNumIterations(Topic_Parameters.LDA_ITERATOIN);
		model.estimate();

		Alphabet dataAlphabet = instances.getDataAlphabet();
		
		
		ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();

		for (int topic = 0; topic < Topic_Parameters.LDA_TOPIC_NUM; topic++) {

			Iterator<IDSorter> iterator = topicSortedWords.get(topic)
					.iterator();

			topicdist data = new topicdist();
			data.topic = topic;

			data.topicdist = model.alpha[topic] / model.alphaSum;
			while (iterator.hasNext()) {
				IDSorter idCountPair = iterator.next();
				data.topicwords
						.put((String) dataAlphabet.lookupObject(idCountPair
								.getID()), idCountPair.getWeight());
				totalword
						.put((String) dataAlphabet.lookupObject(idCountPair
								.getID()), 0.0);
				


			}
			
			topicarray.add(data);
		}
		String LL = Topic_Parameters.LDA_TOPIC_NUM
				+ "	LL/token	:	"
				+ model.formatter
						.format(Math.exp(-(model.modelLogLikelihood() / model.totalTokens)));
		System.out.println(LL);
		

		double totalweight = 0;
		for (int i = 0; i < topicarray.size(); i++) {
			for (String key : topicarray.get(i).topicwords.keySet()) {
				totalweight += topicarray.get(i).topicwords.get(key);
			}
			for (String key : topicarray.get(i).topicwords.keySet()) {
				topicarray.get(i).topicwords.put(key,
						topicarray.get(i).topicwords.get(key) / totalweight);
			}
			totalweight = 0;
		}

		for (String key : totalword.keySet()) {

			for (int i = 0; i < topicarray.size(); i++) {
				if (topicarray.get(i).topicwords.containsKey(key)) {
					totalword
							.put(key,
									totalword.get(key)
											+ (topicarray.get(i).topicwords
													.get(key) * topicarray
													.get(i).topicdist));
				}
			}

		}

		topWord2 = sortByValue(totalword, category,id);
		
		return topWord2;
	}

	public HashMap<Integer, String> sortByValue(Map<String, Double> unsortedMap,String category ,String id)
			throws IOException {
		ArrayList<String> result = new ArrayList<String>();
		HashMap<Integer, String> result2 = new HashMap<Integer, String>();
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(savepath), "UTF-8"));

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Double> sortedMap = new TreeMap(new ValueComparator(
				unsortedMap));
		sortedMap.putAll(unsortedMap);
		Set set = sortedMap.keySet();
		Iterator itr = set.iterator();
		sorted_size = sortedMap.size();
		int num = 0;
		double rank_size = (double) sortedMap.size();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			System.out.println("key : " + key + " rank : " + rank_size);
			try {
				br.write(key + "\t" + rank_size / (double) sortedMap.size()
						+ "\n");
				br.flush();
				if(num ==100)
					break;
				result.add(key);
				result2.put(num, key);
				num++;
				
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rank.put(key, rank_size);
			rank_size--;
		}

		
		br.close();
	
		return result2;
	}


	class ValueComparator implements Comparator<String> {

		Map<String, Double> base;

		public ValueComparator(Map<String, Double> base) {
			this.base = base;
		}

		public int compare(String a, String b) {
			if (base.get(a) > base.get(b)) {
				return -1;
			} else if (base.get(a) == base.get(b)) {
				return 0;
			} else {
				return 1;
			}
		}
	}
}
