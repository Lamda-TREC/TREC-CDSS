package com.IndexServer.socketServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ajou.lamda.metamap.api.MetaMapApiConnector;

import com.IndexServer.Bean.Content;
import com.IndexServer.Bean.evaluationContent;
import com.IndexServer.CoreNlp.StanfordLemmatizer;
import com.IndexServer.LDA.Lda;
import com.IndexServer.PdfBox.PdfToText;
import com.IndexServer.model.DBFacade;
import com.opencsv.CSVReader;

public class TestCBIR {

	private String filePath = "total.csv";
	private String queryfilepath = "query.csv";
	private String[] Category = { "3Dprinting", "SoftwareDefined", "WebScale",
			"AdvancedAnalytics", "Computingeverywhere", "InternetofThings" };
	private int[] categoryquerynum = { 12, 24, 17, 14, 25, 13 };
	private ArrayList<Content> contentList = new ArrayList<Content>();
	private HashMap<Integer, ArrayList<String>> CategoryQuery = new HashMap<Integer, ArrayList<String>>();
	private HashMap<String, String> CategoryById = new HashMap<String, String>();

	private HashMap<String, HashMap<Integer, HashMap<String, evaluationContent>>> CBIRDATA = new HashMap<String, HashMap<Integer, HashMap<String, evaluationContent>>>();
	private HashMap<String, HashMap<String, evaluationContent>> INFODATA = new HashMap<String, HashMap<String, evaluationContent>>();
	private HashMap<String, HashMap<String, evaluationContent>> GTDATA = new HashMap<String, HashMap<String, evaluationContent>>();

	BufferedWriter br = null;
	BufferedWriter br2 = null;
	BufferedWriter br3 = null;
	
	BufferedWriter br4 = null;
	BufferedWriter br5 = null;
	
	BufferedWriter br6 = null;

	public TestCBIR() {
		super();
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(filePath));
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line

			Content data = new Content();
			data.setId(nextLine[0]);
			data.setCategory(nextLine[1]);
			data.setTitle(nextLine[2]);
			data.setDescription(nextLine[3]);

			CategoryById.put(nextLine[0], nextLine[1].replaceAll(" ", "")
					.trim());

			contentList.add(data);

		}

	}

	public boolean makeCBIR(Content data,String Filename) throws IOException {

		boolean result = false;
		Content content = data;

		String id = content.getId();
		String category = content.getCategory();
		String title = content.getTitle();
		String description = content.getDescription();

		try {
			System.out.println("pdf extract text start");
			PdfToText extractionText = new PdfToText();
			extractionText.extractingText(category, id);
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("pdf extract text error");
		}
		System.out.println("pdf extract text end");

		System.out.println("text MorphemeAnalyzer start");
		try {
			/*StanfordLemmatizer morphem = new StanfordLemmatizer();
			morphem.EnglishMorhemeContent(category, id);*/
			
			MetaMapApiConnector metaApi = new MetaMapApiConnector("202.30.23.64");
			metaApi.Connection(true);
			metaApi.EnglishMetMapContent(category, id);
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("text MorphemeAnalyzer error");
		}
		System.out.println("text MorphemeAnalyzer end");

		System.out.println("LDA start");
		Lda lda = new Lda();
		ArrayList<String> index_words = null;
		HashMap<Integer, String> index_words2 = null;
		try {
			index_words2 = lda.extractorWords(category, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("LDA error");
		}

		System.out.println("LDA end");

		/*System.out.println("title summary MorphemeAnalyzer start");

		ArrayList<String> contentInfoWord = null;
		try {
			StanfordLemmatizer morphem = new StanfordLemmatizer();
			contentInfoWord = morphem.EnglishMorhemeContentInfo(title,
					description);
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("title summary MorphemeAnalyzer error");
		}*/
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Filename),true));
		
		for(int i = 0; i < index_words2.size(); i++)
		{
			String Keyword = index_words2.get(i);
			if(i != 19)
			{
				bw.write(Keyword+",");
			}else
			{
				bw.write(Keyword);
				bw.newLine();
			}
			
		}
		
		bw.flush();
		bw.close();
		
		
		
	/*	ArrayList<String> remove_repetition_index_words = new ArrayList<String>();
		DBFacade dbfacade = DBFacade.getInstance();
		result = dbfacade.addIndexWord(content,
				remove_repetition_index_words, index_words2);
		result = dbfacade.addIndexWordCategory(content,
				remove_repetition_index_words, index_words2);
		dbfacade.endConnection();*/
		/*if (index_words2 != null) {

			ArrayList<String> remove_repetition_index_words = null;
			if (contentInfoWord.size() != 0) {
				for (String word : contentInfoWord) {
					if (!index_words2.containsValue(word)) {
						if (index_words == null) {
							index_words = new ArrayList<String>();
						}
						index_words.add(word);
					}
				}
				if (index_words != null) {
					HashSet hs = new HashSet(index_words);
					remove_repetition_index_words = new ArrayList<String>(hs);
				}
			}

			if (remove_repetition_index_words == null) {
				if (index_words == null) {
					index_words = new ArrayList<String>();
				}
				remove_repetition_index_words = index_words;
			}

			System.out.println("DB add Indexwords start");

			DBFacade dbfacade = DBFacade.getInstance();
			result = dbfacade.addIndexWord(content,
					remove_repetition_index_words, index_words2);
			result = dbfacade.addIndexWordCategory(content,
					remove_repetition_index_words, index_words2);
			dbfacade.endConnection();
			if (result) {

				System.out.println("DB add Indexwords end");

			} else {
				System.out.println("DB add Indexwords fail");
			}

			return result;

		}*/

		return result;

	}

	public ArrayList<Content> getContentList() {
		return contentList;
	}

	public void setContentList(ArrayList<Content> contentList) {
		this.contentList = contentList;
	}

	public void testCBIR() {

		try {
			initQeury();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		testRankingQuery();
		//testQuery();
		//testCategoryQuery();
		testInformationIndexQuery();
	}


	public boolean MakeinformationIndex(Content data) {

		boolean result = false;
		Content content = data;

		String id = content.getId();
		String category = content.getCategory();
		String title = content.getTitle();
		String description = content.getDescription();

		ArrayList<String> contentInfoWord = null;
		try {
			StanfordLemmatizer morphem = new StanfordLemmatizer();
			contentInfoWord = morphem.EnglishMorhemeContentInfo(title,
					description);
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("title summary MorphemeAnalyzer error");
		}

		System.out.println("DB add Indexwords start");

		DBFacade dbfacade = DBFacade.getInstance();
		result = dbfacade.addinformationIndex(content, contentInfoWord);
		dbfacade.endConnection();
		if (result) {

			System.out.println("DB add Indexwords end");

		} else {
			System.out.println("DB add Indexwords fail");
		}

		return result;

	}
	
	public void initQeury() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(queryfilepath));
		String[] nextLine;
		int num = 0;
		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			ArrayList<String> category = new ArrayList<String>();
			for (int i = 0; i < nextLine.length; i++) {
				if (categoryquerynum[num] == i)
					break;
				String word = nextLine[i];
				category.add(word);
			}

			CategoryQuery.put(num, category);
			num++;

		}

	}

	
	private void testRankingQuery() {

		StanfordLemmatizer morphem = new StanfordLemmatizer();
		DBFacade dbfacade = DBFacade.getInstance();
		for (int i = 0; i < Category.length; i++) {
			ArrayList<String> categoryWordList = null;
			categoryWordList = CategoryQuery.get(i);
			
			for (String word : categoryWordList) {
				
				ArrayList<String> morpheme = morphem.keywordMorpheme(word);
				for (int indexnum = 1; indexnum < 51; indexnum += 1) {
					
					HashMap<String, Integer> resultList = new HashMap<String, Integer>();
					for (String keyword : morpheme) {
						
						ArrayList<String> resultContent = dbfacade.findContentRanking(
								keyword, indexnum,Category[i]);
						System.out.println(resultContent.size()+" 크기");
						for (String content : resultContent) {
							resultList.put(content, 1);
						}

					}
					String dbname = "index" + indexnum;
					try {
						writeResultRanking(resultList, Category[i], word, dbname);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
		}

	}
	
	private void testQuery() {

		StanfordLemmatizer morphem = new StanfordLemmatizer();
		DBFacade dbfacade = DBFacade.getInstance();
		for (int i = 0; i < Category.length; i++) {
			ArrayList<String> categoryWordList = null;
			categoryWordList = CategoryQuery.get(i);

			for (String word : categoryWordList) {
				ArrayList<String> morpheme = morphem.keywordMorpheme(word);
				for (int indexnum = 5; indexnum < 51; indexnum += 5) {
					HashMap<String, Integer> resultList = new HashMap<String, Integer>();
					for (String keyword : morpheme) {

						ArrayList<String> resultContent = dbfacade.findContent(
								keyword, indexnum);

						for (String content : resultContent) {
							resultList.put(content, 1);
						}

					}
					String dbname = "index" + indexnum;
					try {
						writeResult(resultList, Category[i], word, dbname);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	private void testCategoryQuery() {

		StanfordLemmatizer morphem = new StanfordLemmatizer();
		DBFacade dbfacade = DBFacade.getInstance();
		for (int i = 0; i < Category.length; i++) {
			ArrayList<String> categoryWordList = null;
			categoryWordList = CategoryQuery.get(i);

			for (String word : categoryWordList) {
				ArrayList<String> morpheme = morphem.keywordMorpheme(word);
				for (int indexnum = 5; indexnum < 51; indexnum += 5) {
					HashMap<String, Integer> resultList = new HashMap<String, Integer>();
					for (String keyword : morpheme) {

						ArrayList<String> resultContent = dbfacade
								.findCategoryContent(keyword, indexnum,
										Category[i], indexnum);

						for (String content : resultContent) {
							resultList.put(content, 1);
						}

					}
					String dbname = Category[i] + indexnum;
					try {
						writeResult(resultList, Category[i], word, dbname);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	private void writeResultRanking(HashMap<String, Integer> result, String category,
			String keyword, String dbname) throws IOException {

		// int[] categoryNum = { 0, 0, 0, 0, 0, 0 };
		int categoryNum = 0;
		String resultfilepath = "result/" + category + "_" + dbname + ".csv";

		ArrayList<String> contentList = new ArrayList<String>();
		try {
			br = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(resultfilepath, true), "UTF-8"));

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// br.write("Keyword,3dprinting,softwaredefined,webscale,advancedanalytics,iteverywhere,internetofthings,contentid\n");

		Set key = result.keySet();

		for (Iterator iterator = key.iterator(); iterator.hasNext();) {
			String content = (String) iterator.next();
			contentList.add(content);
			String contentCategory = CategoryById.get(content);
			System.out.println(content);

			if (category.equals(contentCategory)) {
				categoryNum++;
			}

			/*
			 * for (int i = 0; i < Category.length; i++) { if
			 * (Category[i].equals(contentCategory)) { categoryNum[i]++; } }
			 */

		}

		br.write(keyword + ",");
		br.write(categoryNum + ",");
		/*
		 * for (int i = 0; i < categoryNum.length; i++) {
		 * br.write(categoryNum[i] + ","); }
		 */

		int len = contentList.size();
		for (int i = 0; i < len; i++) {
			if (i == len - 1) {
				br.write(contentList.get(i));
				break;
			}
			br.write(contentList.get(i) + ",");
		}
		br.write("\n");
		br.flush();
		br.close();
	}

	private void testInformationIndexQuery() {

		StanfordLemmatizer morphem = new StanfordLemmatizer();
		DBFacade dbfacade = DBFacade.getInstance();
		for (int i = 0; i < Category.length; i++) {
			ArrayList<String> categoryWordList = null;
			categoryWordList = CategoryQuery.get(i);

			for (String word : categoryWordList) {
				ArrayList<String> morpheme = morphem.keywordMorpheme(word);

				HashMap<String, Integer> resultList = new HashMap<String, Integer>();
				for (String keyword : morpheme) {

					ArrayList<String> resultContent = dbfacade
							.findInformationIndex(keyword,Category[i]);

					for (String content : resultContent) {
						resultList.put(content, 1);
					}

				}
				String dbname = "informationIndex";
				try {
					writeResult(resultList, Category[i], word, dbname);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private void writeResult(HashMap<String, Integer> result, String category,
			String keyword, String dbname) throws IOException {

		// int[] categoryNum =	 { 0, 0, 0, 0, 0, 0 };
		int categoryNum = 0;
		String resultfilepath = "result/" + category + "_" + dbname + ".csv";

		ArrayList<String> contentList = new ArrayList<String>();
		try {
			br = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(resultfilepath, true), "UTF-8"));

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// br.write("Keyword,3dprinting,softwaredefined,webscale,advancedanalytics,iteverywhere,internetofthings,contentid\n");

		Set key = result.keySet();

		for (Iterator iterator = key.iterator(); iterator.hasNext();) {
			String content = (String) iterator.next();
			contentList.add(content);
			String contentCategory = CategoryById.get(content);
			System.out.println(content);

			if (category.equals(contentCategory)) {
				categoryNum++;
			}

			/*
			 * for (int i = 0; i < Category.length; i++) { if
			 * (Category[i].equals(contentCategory)) { categoryNum[i]++; } }
			 */

		}

		br.write(keyword + ",");
		br.write(categoryNum + ",");
		/*
		 * for (int i = 0; i < categoryNum.length; i++) {
		 * br.write(categoryNum[i] + ","); }
		 */

		int len = contentList.size();
		for (int i = 0; i < len; i++) {
			if (i == len - 1) {
				br.write(contentList.get(i));
				break;
			}
			br.write(contentList.get(i) + ",");
		}
		br.write("\n");
		br.flush();
		br.close();
	}


	public void testEvaluation() {
		
		try {
			initWriter();
			initQeury();
			initEvaluationRankingCBIR();
			//initEvaluationCBIR();
			initEvaluationInfo();
			initEvaluationGt();
			evaluationCBIR();
			evaluationINFO();
			cloasWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	private void initWriter(){
		
		String resultfilepathprecision = "result/totalPrecision.csv";
		String resultfilepathrecall = "result/totalRecall.csv";
		String resultfilepathfmeasure = "result/totalfmeasure.csv";

		try {
			br3 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(resultfilepathprecision, true),
					"UTF-8"));
			br4 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(resultfilepathrecall, true),
					"UTF-8"));

			br5 = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(resultfilepathfmeasure, true),
					"UTF-8"));
			br3.write("word,Category,AvergPrecision\n");
			
			br4.write("word,Category,AvergRecall\n");
			br5.write("word,Category,F-Measure\n");
			
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	private void cloasWriter(){
	
		try {
			br3.flush();
			br4.flush();
			br5.flush();
			br3.close();
			br4.close();
			br5.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initEvaluationRankingCBIR() throws IOException {
		
		for (int i = 0; i < Category.length; i++) {
			HashMap<Integer, HashMap<String, evaluationContent>> categoryHash = new HashMap<Integer, HashMap<String, evaluationContent>>();
			for (int j = 1; j < 51; j += 1) {
				HashMap<String, evaluationContent> keywordHash = new HashMap<String, evaluationContent>();
				String filepath = "result/"+Category[i] + "_" + "index" + j + ".csv";
				//String filepath = "result/"+Category[i] + "_" + Category[i] + j + ".csv";
				CSVReader reader = new CSVReader(new FileReader(filepath));
				String[] nextLine;
				while ((nextLine = reader.readNext()) != null) {

					if(nextLine[0].equals(""))
					{
						break;
					}
					evaluationContent content = new evaluationContent();
					String keyword = nextLine[0];
					int docNum =Integer.valueOf(nextLine[1]);
					
					ArrayList<String> contentIdList = new ArrayList<String>();
					for (int k = 2; k < 2 + docNum; k++) {
						contentIdList.add(nextLine[k]);
					}

					content.setKeyword(keyword);
					content.setDocumentNum(docNum);
					content.setContentId(contentIdList);
					keywordHash.put(keyword, content);

				}

				categoryHash.put(j, keywordHash);

			}

			CBIRDATA.put(Category[i], categoryHash);

		}

	}
	
	
	private void initEvaluationCBIR() throws IOException {

		for (int i = 0; i < Category.length; i++) {
			HashMap<Integer, HashMap<String, evaluationContent>> categoryHash = new HashMap<Integer, HashMap<String, evaluationContent>>();
			for (int j = 1; j < 51; j += 1) {
				HashMap<String, evaluationContent> keywordHash = new HashMap<String, evaluationContent>();
				String filepath = "result/"+Category[i] + "_" + "index" + j + ".csv";
				//String filepath = "result/"+Category[i] + "_" + Category[i] + j + ".csv";
				CSVReader reader = new CSVReader(new FileReader(filepath));
				String[] nextLine;
				while ((nextLine = reader.readNext()) != null) {

					if(nextLine[0].equals(""))
					{
						break;
					}
					evaluationContent content = new evaluationContent();
					String keyword = nextLine[0];
					int docNum = Integer.valueOf(nextLine[1]);
					ArrayList<String> contentIdList = new ArrayList<String>();
					for (int k = 2; k < 2 + docNum; k++) {
						contentIdList.add(nextLine[k]);
					}

					content.setKeyword(keyword);
					content.setDocumentNum(docNum);
					content.setContentId(contentIdList);

					keywordHash.put(keyword, content);

				}

				categoryHash.put(j, keywordHash);

			}

			CBIRDATA.put(Category[i], categoryHash);

		}

	}

	private void initEvaluationInfo() throws IOException {

		for (int i = 0; i < Category.length; i++) {

			HashMap<String, evaluationContent> keywordHash = new HashMap<String, evaluationContent>();
			String filepath ="result/"+ Category[i] + "_informationIndex.csv";
			CSVReader reader = new CSVReader(new FileReader(filepath));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {

				if(nextLine[0].equals(""))
				{
					break;
				}
				evaluationContent content = new evaluationContent();
				String keyword = nextLine[0];
				int docNum = Integer.valueOf(nextLine[1]);
				ArrayList<String> contentIdList = new ArrayList<String>();
				for (int k = 2; k < 2 + docNum; k++) {
					contentIdList.add(nextLine[k]);
				}

				content.setKeyword(keyword);
				content.setDocumentNum(docNum);
				content.setContentId(contentIdList);

				keywordHash.put(keyword, content);

			}
			INFODATA.put(Category[i], keywordHash);
		}

	}

	private void initEvaluationGt() throws IOException {
		for (int i = 0; i < Category.length; i++) {

			HashMap<String, evaluationContent> keywordHash = new HashMap<String, evaluationContent>();
			String filepath = "result/"+Category[i] + "_GroundTruth.csv";
			CSVReader reader = new CSVReader(new FileReader(filepath));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {

				if(nextLine[0].equals(""))
				{
					break;
				}
				evaluationContent content = new evaluationContent();
				String keyword = nextLine[0];
				int docNum = Integer.valueOf(nextLine[1]);
				ArrayList<String> contentIdList = new ArrayList<String>();
				for (int k = 2; k < 2 + docNum; k++) {
					contentIdList.add(nextLine[k]);
				}

				content.setKeyword(keyword);
				content.setDocumentNum(docNum);
				content.setContentId(contentIdList);

				keywordHash.put(keyword, content);

			}
			GTDATA.put(Category[i], keywordHash);
		}

	}

	private double calculatePrecision(String keyword,
			evaluationContent retrival, evaluationContent relavant) {
		double result = 0;

		double intersection = 0;
		for (String content : retrival.getContentId()) {
			if (relavant.getContentId().contains(content)) {
				intersection++;
			}
		}

		if (retrival.getDocumentNum() != 0) {
			result = intersection / retrival.getDocumentNum();
		} else {
			result = 0;
		}

		return result;

	}

	private double calculateRecall(String keyword, evaluationContent retrival,
			evaluationContent relavant) {

		double result = 0;

		double intersection = 0;
		for (String content : retrival.getContentId()) {
			if (relavant.getContentId().contains(content)) {
				intersection++;
			}
		}
		//System.out.println(keyword);
		if (relavant.getDocumentNum() != 0) {
			result = intersection / relavant.getDocumentNum();
		} else {
			result = 0;
		}

		return result;
	}

	private void evaluationCBIR() throws IOException {

		for (int i = 0; i < Category.length; i++) {
			String resultfilepathprecision = "result/" + Category[i]
					+ "ResultPrecision.csv";
			String resultfilepathrecall = "result/" + Category[i]
					+ "ResultRecall.csv";
			String resultfilepathfmeasure = "result/" + Category[i]
					+ "Resultfmeasure.csv";
			

			try {
				br = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(resultfilepathprecision, true),
						"UTF-8"));
				br2 = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(resultfilepathrecall, true),
						"UTF-8"));
				br6 = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(resultfilepathfmeasure, true),
						"UTF-8"));

			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			br.write("keyword,");
			br2.write("keyword,");
			br6.write("keyword,");
			for (int k = 0; k < CategoryQuery.get(i).size(); k++) {
				String keyword = CategoryQuery.get(i).get(k);
				if (k == CategoryQuery.get(i).size()-1) {
					br.write(keyword + "\n");
					br2.write(keyword + "\n");
					br6.write(keyword + "\n");
				} else {
					br.write(keyword + ",");
					br2.write(keyword + ",");
					br6.write(keyword + ",");
				}
			}

			for (int j = 1; j < 51; j += 1) {
				HashMap<String, evaluationContent> retrival = CBIRDATA.get(
						Category[i]).get(j);

				HashMap<String, evaluationContent> relavant = GTDATA
						.get(Category[i]);

				HashMap<String, Double> pricisionResult = new HashMap<String, Double>();

				HashMap<String, Double> recallResult = new HashMap<String, Double>();
				HashMap<String, Double> fmeasureResult = new HashMap<String, Double>();
				for (int k = 0; k < CategoryQuery.get(i).size(); k++) {
					String keyword = CategoryQuery.get(i).get(k);

					
					double pricison = calculatePrecision(keyword,
							retrival.get(keyword), relavant.get(keyword));
					double recall = calculateRecall(keyword,
							retrival.get(keyword), relavant.get(keyword));
					double fmeasure = 0;
					if(pricison == 0 && recall == 0)
					{
						fmeasure = 0;
					}
					else
					{
						fmeasure = (2*pricison*recall) / (pricison+recall);
					}
					
					pricisionResult.put(keyword, pricison);
					recallResult.put(keyword, recall);				
					fmeasureResult.put(keyword, fmeasure);
				}

				br.write(j + ",");
				br2.write(j + ",");
				br6.write(j + ",");
				double totalprecision = 0;
				double totalrecall = 0;
				double totalfmeasure = 0;
				for (int k = 0; k < CategoryQuery.get(i).size(); k++) {
					String keyword = CategoryQuery.get(i).get(k);

					if (k == CategoryQuery.get(i).size() - 1) {
						br.write(pricisionResult.get(keyword) + "\n");
						br2.write(recallResult.get(keyword) + "\n");
						br6.write(fmeasureResult.get(keyword) + "\n");
					} else {
						br.write(pricisionResult.get(keyword) + ",");
						br2.write(recallResult.get(keyword) + ",");
						br6.write(fmeasureResult.get(keyword) + ",");
						
					}

					totalprecision+=pricisionResult.get(keyword);
					totalrecall+=recallResult.get(keyword);
					totalfmeasure+=fmeasureResult.get(keyword);

				}
				
				double avergPrevision = totalprecision / pricisionResult.size();
				double avergRecall = totalrecall / recallResult.size();
				double fmeasure = totalfmeasure/fmeasureResult.size();
				
				br3.write(j+","+Category[i]+","+avergPrevision+"\n");
				br4.write(j+","+Category[i]+","+avergRecall+"\n");
				br5.write(j+","+Category[i]+","+fmeasure+"\n");
				
				

			}
			br.flush();
			br2.flush();
			br6.flush();
			br.close();
			br2.close();
			br6.close();

		}

	}

	private void evaluationINFO() throws IOException {
		for (int i = 0; i < Category.length; i++) {
			String resultfilepathprecision = "result/" + Category[i]
					+ "InformationResultPrecision.csv";
			String resultfilepathrecall = "result/" + Category[i]
					+ "InformationResultRecall.csv";
			String resultfilepathfmeasure = "result/" + Category[i]
					+ "InformationResultfmeasure.csv";

			try {
				br = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(resultfilepathprecision, true),
						"UTF-8"));
				br2 = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(resultfilepathrecall, true),
						"UTF-8"));
				br6 = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(resultfilepathfmeasure, true),
						"UTF-8"));

			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			br.write("keyword,");
			br2.write("keyword,");
			br6.write("keyword,");
			for (int k = 0; k < CategoryQuery.get(i).size(); k++) {
				String keyword = CategoryQuery.get(i).get(k);
				if (k == CategoryQuery.get(i).size() - 1) {
					br.write(keyword + "\n");
					br2.write(keyword + "\n");
					br6.write(keyword + "\n");
					
				} else {
					br.write(keyword + ",");
					br2.write(keyword + ",");
					br6.write(keyword + ",");
				}
			}

			HashMap<String, evaluationContent> retrival = INFODATA
					.get(Category[i]);

			HashMap<String, evaluationContent> relavant = GTDATA
					.get(Category[i]);

			HashMap<String, Double> pricisionResult = new HashMap<String, Double>();

			HashMap<String, Double> recallResult = new HashMap<String, Double>();
			HashMap<String, Double> fmeasureResult = new HashMap<String, Double>();
			for (int k = 0; k < CategoryQuery.get(i).size(); k++) {
				String keyword = CategoryQuery.get(i).get(k);

				double pricison = calculatePrecision(keyword,
						retrival.get(keyword), relavant.get(keyword));
				double recall = calculateRecall(keyword, retrival.get(keyword),
						relavant.get(keyword));
				
				double fmeasure = 0;
				if(pricison == 0 && recall == 0)
				{
					fmeasure = 0;
				}
				else
				{
					fmeasure = (2*pricison*recall) / (pricison+recall);
				}
				pricisionResult.put(keyword, pricison);
				recallResult.put(keyword, recall);
				fmeasureResult.put(keyword, fmeasure);

			}

			br.write("info,");
			br2.write("info,");
			br6.write("info,");
			double totalprecision = 0;
			double totalrecall = 0;
			double totalfmeasure = 0;
			for (int k = 0; k < CategoryQuery.get(i).size(); k++) {
				String keyword = CategoryQuery.get(i).get(k);

				if (k == CategoryQuery.get(i).size() - 1) {
					br.write(pricisionResult.get(keyword) + "\n");
					br2.write(recallResult.get(keyword) + "\n");
					br6.write(fmeasureResult.get(keyword) + "\n");
				} else {
					br.write(pricisionResult.get(keyword) + ",");
					br2.write(recallResult.get(keyword) + ",");
					br6.write(fmeasureResult.get(keyword) + "\n");
					
				}

				totalprecision+=pricisionResult.get(keyword);
				totalrecall+=recallResult.get(keyword);
				totalfmeasure+=fmeasureResult.get(keyword);

			}
			
			double avergPrevision = totalprecision / pricisionResult.size();
			double avergRecall = totalrecall / recallResult.size();
			double fmeasure = totalfmeasure/fmeasureResult.size();
			
			for(int j = 1; j <51;j+=1)
			{
				br3.write(j+","+Category[i]+"_simple,"+avergPrevision+"\n");
				br4.write(j+","+Category[i]+"_simple,"+avergRecall+"\n");
				br5.write(j+","+Category[i]+"_simple,"+fmeasure+"\n");
				
			}
			
			br.flush();
			br2.flush();
			br6.flush();
			br.close();
			br2.close();
			br6.close();


		}
	
	}

}
