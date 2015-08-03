/**
 * 
 */
package com.nxmlindex.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxmlindex.NxmlIndexApplication;
import com.nxmlindex.common.vo.ArticleAbstract;
import com.nxmlindex.common.vo.ArticleBody;
import com.nxmlindex.common.vo.Section;
import com.nxmlindex.mongodb.ArticleAbstractRepository;
import com.nxmlindex.mongodb.ArticleAnswerDataRepository;
import com.nxmlindex.mongodb.ArticleBodyRepository;
import com.nxmlindex.mongodb.ArticleDevideData1Repository;
import com.nxmlindex.mongodb.ArticleDevideData2Repository;
import com.nxmlindex.mongodb.ArticleDevideData3Repository;
import com.nxmlindex.mongodb.ArticleSmallDataRepository;
import com.nxmlindex.nlptools.CoreNLPTokenizer;
import com.nxmlindex.nlptools.TextPreprocess;
import com.nxmlindex.util.DevideData;
import com.nxmlindex.util.NxmlListLoader;
import com.nxmlindex.util.TFIDF;

/**
 * <pre>
 * com.nxmlindex.service
 *   |_ SVMDictionaryService.java
 * </pre>
 * 
 * Desc :
 * 
 * @Company : LAMDA in Ajou Univ
 * @Author : ChaMunsu
 * @Date : 2015. 7. 21. 오후 8:38:20
 * @Version :
 */

@Service
public class SVMDictionaryService {

	@Autowired
	private ArticleAbstractRepository articleAbstractRepository;

	@Autowired
	private ArticleBodyRepository articleBodyRepostory;

	@Autowired
	private ArticleSmallDataRepository articleSmallDataRepository;

	@Autowired
	private ArticleAnswerDataRepository articleAnswerDataRepository;

	@Autowired
	private ArticleDevideData1Repository articleDevide1DataRepository;

	@Autowired
	private ArticleDevideData2Repository articleDevide2DataRepository;

	@Autowired
	private ArticleDevideData3Repository articleDevide3DataRepository;

	private NxmlListLoader nxmlListLoader;
	private HashMap<Integer, String> answerNxml;

	ArrayList<Integer> topicNumber[] = new ArrayList[3];
	private String diaType = "dia";
	private String testType = "test";
	private String treatType = "treat";
	private int diagnum;
	private int testnum;
	private int treatnum;
	private int categorynumArray[] = new int[3];
	private int startAnswerNumArray[] = new int[3];
	private int endAnswerNumArray[] = new int[3];
	private double trainingrate = 0.8;

	private CoreNLPTokenizer tokenizer;
	private TextPreprocess preprocessing;
	
	private BufferedWriter bwDiag;
	private BufferedWriter bwtest;
	private BufferedWriter bwtreat;

	public void init() {

		DevideData dvide = new DevideData();

		nxmlListLoader = new NxmlListLoader();
		try {
			nxmlListLoader.loadNxmlList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		answerNxml = nxmlListLoader.getNxmlAnswerHashMap();

		startAnswerNumArray[0] = 0;
		startAnswerNumArray[1] = 1119;
		startAnswerNumArray[2] = 2134;

		endAnswerNumArray[0] = 1119;
		endAnswerNumArray[1] = 2134;
		endAnswerNumArray[2] = 3356;
		topicNumber[0] = dvide.randomSelector(startAnswerNumArray[0],
				endAnswerNumArray[0]);
		topicNumber[1] = dvide.randomSelector(startAnswerNumArray[1],
				endAnswerNumArray[1]);
		topicNumber[2] = dvide.randomSelector(startAnswerNumArray[2],
				endAnswerNumArray[2]);

		diagnum =  (int) (topicNumber[0].size() * trainingrate);
		testnum = (int) (topicNumber[1].size() * trainingrate);
		treatnum = (int) (topicNumber[2].size() * trainingrate);

		categorynumArray[0] = diagnum;

		categorynumArray[1] = testnum;

		categorynumArray[2] = treatnum;

		tokenizer = new CoreNLPTokenizer();
		preprocessing = new TextPreprocess();
		try {
			preprocessing.loadStopword();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			bwDiag = new BufferedWriter(new FileWriter(new File("0_TrainData.txt")));
			bwtest = new BufferedWriter(new FileWriter(new File("1_TrainData.txt")));
			bwtreat = new BufferedWriter(new FileWriter(new File("2_TrainData.txt")));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initTopic(){
		
		
	}

	public void constructDictionary() {

		for (int i = 0; i < 3; i++) {
			analizeDocument(i);
		
			removeStopword(i);
			
			try {
				writeTestData(i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			bwDiag.close();
			bwtest.close();
			bwtreat.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		calculateTFIDF();
		removeDuplicate();
		
		try {
			writeResult();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		

	}
	
	public void writeTestData(int categoryNum) throws Exception
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(categoryNum+"_TestData.txt")));
		for(int i =categorynumArray[categoryNum];i<topicNumber[categoryNum].size();i++ )
		{
			System.out.println(i+"끝");
			try {
				if(topicNumber[categoryNum].size() != i)
				{
					int index = topicNumber[categoryNum].get(i);
					String pmcid = answerNxml.get(index);
					bw.write(pmcid);
					bw.newLine();
					bw.flush();
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		bw.close();
	}

	public void analizeDocument(int categoryNum) {
		
		

		for (int i = 0; i < categorynumArray[categoryNum]; i++) {
			
			int index = topicNumber[categoryNum].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			writeTrainingData(categoryNum,pmcid);
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);

			if (articleBody != null) {
				String title = articleBody.getTitle();

				String match = "[^a-zA-Z]";
				title = title.replaceAll(match, " ");
				String match2 = "\\s{2,}";
				title = title.replaceAll(match2, " ");
				System.out.println(title);
				List<String> titleresult = tokenizer.lemmatokenize(title);

				for (String token : titleresult) {
					token = token.toLowerCase();
					if (NxmlIndexApplication.CategoryHashMap.get(categoryNum)
							.size() == 0) {
						NxmlIndexApplication.CategoryHashMap.get(categoryNum)
								.put(token, 1);
					} else {
						if (NxmlIndexApplication.CategoryHashMap.get(
								categoryNum).containsKey(token)) {
							int count = NxmlIndexApplication.CategoryHashMap
									.get(categoryNum).get(token);
							count++;
							NxmlIndexApplication.CategoryHashMap.get(
									categoryNum).put(token, count);

						} else {
							NxmlIndexApplication.CategoryHashMap.get(
									categoryNum).put(token, 1);
						}
					}
				}

			}

			System.out.println("본문 분석 시작");

			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {

				} else {

					if (articleAbstract.getSectionList() != null) {

						System.out.println("sectionList size : "
								+ articleAbstract.getSectionList().size());
						if (articleAbstract.getSectionList().size() > 0) {
							for (Section section : articleAbstract
									.getSectionList()) {
								String text = section.getParagraphs();
								// System.out.println(text);
								String paragraphs = section.getParagraphs();
								if (paragraphs != null) {
									String match = "[^a-zA-Z]";
									paragraphs = paragraphs.replaceAll(match,
											" ");
									String match2 = "\\s{2,}";
									paragraphs = paragraphs.replaceAll(match2, " ");
									System.out.println(paragraphs);
									List<String> paragraphsResult = tokenizer
											.lemmatokenize(paragraphs);

									for (String token : paragraphsResult) {
										token = token.toLowerCase();

										if (NxmlIndexApplication.CategoryHashMap
												.get(categoryNum).containsKey(
														token)) {
											int count = NxmlIndexApplication.CategoryHashMap
													.get(categoryNum)
													.get(token);
											count++;
											NxmlIndexApplication.CategoryHashMap
													.get(categoryNum).put(
															token, count);

										} else {
											NxmlIndexApplication.CategoryHashMap
													.get(categoryNum).put(
															token, 1);
										}

									}
								}

							}
						}

					}

					if (articleBody.getSectionList() != null) {

						System.out.println("sectionList size : "
								+ articleBody.getSectionList().size());
						if (articleBody.getSectionList().size() > 0) {
							for (Section section : articleBody.getSectionList()) {
								String text = section.getParagraphs();
								// System.out.println(text);
								String paragraphs = section.getParagraphs();
								if (paragraphs != null) {
									String match = "[^a-zA-Z]";
									paragraphs = paragraphs.replaceAll(match,
											" ");
									String match2 = "\\s{2,}";
									paragraphs = paragraphs.replaceAll(match2, " ");
									System.out.println(paragraphs);
									List<String> paragraphsResult = tokenizer
											.lemmatokenize(paragraphs);

									for (String token : paragraphsResult) {
										token = token.toLowerCase();

										if (NxmlIndexApplication.CategoryHashMap
												.get(categoryNum).containsKey(
														token)) {
											int count = NxmlIndexApplication.CategoryHashMap
													.get(categoryNum)
													.get(token);
											count++;
											NxmlIndexApplication.CategoryHashMap
													.get(categoryNum).put(
															token, count);

										} else {
											NxmlIndexApplication.CategoryHashMap
													.get(categoryNum).put(
															token, 1);
										}

									}
								}

							}
						}

					}

				}

			}
			System.out.println(i + "끝");
		}

	}
	
	public void writeTrainingData(int categoryNum,String pmcid ){
		
		switch (categoryNum) {
		case 0:
			try {
				bwDiag.write(pmcid);
				bwDiag.newLine();
				bwDiag.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 1:
			try {
				bwtest.write(pmcid);
				bwtest.newLine();
				bwtest.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				bwtreat.write(pmcid);
				bwtreat.newLine();
				bwtreat.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	public void removeStopword(int categoryNum) {

		for (String stopword : preprocessing.getStopword()) {
			NxmlIndexApplication.CategoryHashMap.get(categoryNum).remove(
					stopword);
			NxmlIndexApplication.CategoryHashMap.get(categoryNum).remove(
					stopword);
			NxmlIndexApplication.CategoryHashMap.get(categoryNum).remove(
					stopword);
		}

	}

	public void calculateTFIDF() {

		System.out.println("TFIDF 시작");
		TFIDF tfidf = new TFIDF();
		tfidf.calculateTF();
		tfidf.calculateIDF();
		tfidf.calculateTFIDF();
	}
	
	public void writeResult() throws Exception{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("SVM_"+
				diaType+"_Word_TFIDF.csv")));
		bw.write("Word,TFIDF");
		bw.newLine();
		

		Iterator it = NxmlIndexApplication.sortByValue(NxmlIndexApplication.DiaTFIDFHashMap)
				.iterator();

		while (it.hasNext()) {

			String semantic = (String) it.next();
			double tfidfvalue = NxmlIndexApplication.DiaTFIDFHashMap.get(semantic);
			bw.write(semantic + "," + tfidfvalue);
			bw.newLine();

		}

		bw.flush();
		bw.close();
		
		bw = new BufferedWriter(new FileWriter(new File("SVM_"+
				diaType+"_NoDuplicate_Word_TFIDF.csv")));
		bw.write("Word,TFIDF");
		bw.newLine();
		

		it = NxmlIndexApplication.sortByValue(NxmlIndexApplication.DiaNoDuplicateTFIDFHashMap)
				.iterator();

		while (it.hasNext()) {

			String semantic = (String) it.next();
			double tfidfvalue = NxmlIndexApplication.DiaNoDuplicateTFIDFHashMap.get(semantic);
			bw.write(semantic + "," + tfidfvalue);
			bw.newLine();

		}

		bw.flush();
		bw.close();
		
		bw = new BufferedWriter(new FileWriter(new File("SVM_"+
				testType+"_Word_TFIDF.csv")));
		bw.write("Word,TFIDF");
		bw.newLine();
		

		it = NxmlIndexApplication.sortByValue(NxmlIndexApplication.TestTFIDFHashMap)
				.iterator();

		while (it.hasNext()) {

			String semantic = (String) it.next();
			double tfidfvalue = NxmlIndexApplication.TestTFIDFHashMap.get(semantic);
			bw.write(semantic + "," + tfidfvalue);
			bw.newLine();

		}

		bw.flush();
		bw.close();
		
		bw = new BufferedWriter(new FileWriter(new File("SVM_"+
				testType+"_NoDuplicate_Word_TFIDF.csv")));
		bw.write("Word,TFIDF");
		bw.newLine();
		

		it = NxmlIndexApplication.sortByValue(NxmlIndexApplication.TestNoDuplicateTFIDFHashMap)
				.iterator();

		while (it.hasNext()) {

			String semantic = (String) it.next();
			double tfidfvalue = NxmlIndexApplication.TestNoDuplicateTFIDFHashMap.get(semantic);
			bw.write(semantic + "," + tfidfvalue);
			bw.newLine();

		}

		bw.flush();
		bw.close();
		
		
		bw = new BufferedWriter(new FileWriter(new File("SVM_"+
				treatType+"_Word_TFIDF.csv")));
		bw.write("Word,TFIDF");
		bw.newLine();
		

		it = NxmlIndexApplication.sortByValue(NxmlIndexApplication.TreatTFIDFHashMap)
				.iterator();

		while (it.hasNext()) {

			String semantic = (String) it.next();
			double tfidfvalue = NxmlIndexApplication.TreatTFIDFHashMap.get(semantic);
			bw.write(semantic + "," + tfidfvalue);
			bw.newLine();

		}

		bw.flush();
		bw.close();
		
		bw = new BufferedWriter(new FileWriter(new File("SVM_"+
				treatType+"_NoDuplicate_Word_TFIDF.csv")));
		bw.write("Word,TFIDF");
		bw.newLine();
		

		it = NxmlIndexApplication.sortByValue(NxmlIndexApplication.TreatNoDuplicateTFIDFHashMap)
				.iterator();

		while (it.hasNext()) {

			String semantic = (String) it.next();
			double tfidfvalue = NxmlIndexApplication.TreatNoDuplicateTFIDFHashMap.get(semantic);
			bw.write(semantic + "," + tfidfvalue);
			bw.newLine();

		}

		bw.flush();
		bw.close();
		
		bw = new BufferedWriter(new FileWriter(new File(
				"SVM_Total_Word_TFIDF.csv")));
		bw.write("Word,DiagTFIDF,TestTFIDf,TreatTFIDF");
		bw.newLine();
		

		it = NxmlIndexApplication.TotalTFIDFHashMap.keySet().iterator();

		while (it.hasNext()) {

			String semantic = (String) it.next();
			HashMap<Integer, Double> value = NxmlIndexApplication.TotalTFIDFHashMap.get(semantic);
			
			bw.write(semantic + ",");
			if(value.containsKey(0))
			{
				bw.write(value.get(0)+",");
			}else
			{
				bw.write(0+",");
			}
			if(value.containsKey(1))
			{
				bw.write(value.get(1)+",");
			}else
			{
				bw.write(0+",");
			}
			if(value.containsKey(2))
			{
				bw.write(value.get(2)+"");
			}else
			{
				bw.write(0+",");
			}
			bw.newLine();

		}

		bw.flush();
		bw.close();
	}
	
	
	public void removeDuplicate(){
		
		
		Iterator<String> it = NxmlIndexApplication.DiaTFIDFHashMap.keySet().iterator();
		
		while(it.hasNext())
		{
			String word = it.next();
			
			double score = NxmlIndexApplication.DiaTFIDFHashMap.get(word);
			
			int index = 0; 
			
			if(NxmlIndexApplication.TestTFIDFHashMap.containsKey(word) && NxmlIndexApplication.TreatTFIDFHashMap.containsKey(word))
			{
			
				double score2 = NxmlIndexApplication.TestTFIDFHashMap.get(word);
				
				double score3 = NxmlIndexApplication.TreatTFIDFHashMap.get(word);
				
				
				if (score > score2)
				{
					if(score > score3)
					{
						index = 1;
					}else
					{
						index = 3;
					}
				}else
				{
					if(score2 > score3)
					{
						index = 2;
					}else
					{
						index = 3;
					}
				}
				
				
				
			}else if(NxmlIndexApplication.TestTFIDFHashMap.containsKey(word))
			{
				double score2 = NxmlIndexApplication.TestTFIDFHashMap.get(word);
			
				
				if(score > score2)
				{
					index = 1;
				}else
				{
					score = 2;
				}
				
			}else if(NxmlIndexApplication.TreatTFIDFHashMap.containsKey(word))
			{
				double score2 = NxmlIndexApplication.TreatTFIDFHashMap.get(word);
				
				if(score > score2)
				{
					index = 1;
				}else
				{
					score = 2;
				}
			}
			
			switch (index) {
			case 1:
				NxmlIndexApplication.DiaNoDuplicateTFIDFHashMap.put(word, score);
				break;
			case 2:
				score = NxmlIndexApplication.TestTFIDFHashMap.get(word);
				NxmlIndexApplication.TestNoDuplicateTFIDFHashMap.put(word, score);
				break;
			case 3:
				score = NxmlIndexApplication.TreatTFIDFHashMap.get(word);
				NxmlIndexApplication.TreatNoDuplicateTFIDFHashMap.put(word, score);
				break;

			default:
				break;
			}
		}
		
		it = NxmlIndexApplication.TestTFIDFHashMap.keySet().iterator();
		
		while(it.hasNext()){
			
			String word = it.next();
			
			double score = NxmlIndexApplication.TestTFIDFHashMap.get(word);
			
			int index = 0;

			if(NxmlIndexApplication.TreatTFIDFHashMap.containsKey(word))
			{
				double score2 = NxmlIndexApplication.TreatTFIDFHashMap.get(word);
				
				if(score > score2)
				{
					index = 2;
				}else
				{
					score = 3;
				}
			}
			
			switch (index) {
			case 1:
				NxmlIndexApplication.DiaNoDuplicateTFIDFHashMap.put(word, score);
				break;
			case 2:
				score = NxmlIndexApplication.TestTFIDFHashMap.get(word);
				NxmlIndexApplication.TestNoDuplicateTFIDFHashMap.put(word, score);
				break;
			case 3:
				score = NxmlIndexApplication.TreatTFIDFHashMap.get(word);
				NxmlIndexApplication.TreatNoDuplicateTFIDFHashMap.put(word, score);
				break;

			default:
				break;
			}
		}
		
	}

}
