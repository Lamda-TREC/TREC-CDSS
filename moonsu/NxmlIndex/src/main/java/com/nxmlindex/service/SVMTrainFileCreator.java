/**
 * 
 */
package com.nxmlindex.service;

import gov.nih.nlm.nls.metamap.Result;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nxmlindex.MetaMap.MetaMapApiConnector;
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
import com.opencsv.CSVReader;

/**
 * <pre>
 * com.nxmlindex.service
 *   |_ SVMTrainFileCreator.java
 * </pre>
 * 
 * Desc :
 * 
 * @Company : LAMDA in Ajou Univ
 * @Author : ChaMunsu
 * @Date : 2015. 7. 24. 오후 10:43:51
 * @Version :
 */

@Service
public class SVMTrainFileCreator {

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

	private HashMap<Integer, ArrayList<String>> TraingDataHashMap = new HashMap<Integer, ArrayList<String>>();

	private HashMap<Integer, HashMap<String, Double>> CategoryDictionaryHashMap = new HashMap<Integer, HashMap<String, Double>>();

	// private String dicPath[] =
	// {"SVM_dia_Word_TFIDF.csv","SVM_test_Word_TFIDF.csv","SVM_treat_Word_TFIDF.csv"};
	// private String dicPath[] =
	// {"SVM_dia_NoDuplicate_Word_TFIDF.csv","SVM_test_NoDuplicate_Word_TFIDF.csv","SVM_treat_NoDuplicate_Word_TFIDF.csv"};
	// private String dicPath[] =
	// {"dia_MetaMapWord_TFIDF.csv","test_MetaMapWord_TFIDF.csv","treat_MetaMapWord_TFIDF.csv"};
	private String dicPath[] = { "dia_MetaMapDocument_TFIDF.csv",
			"test_MetaMapDocument_TFIDF.csv", "treat_MetaMapDocument_TFIDF.csv" };

	private CoreNLPTokenizer tokenizer;
	private TextPreprocess preprocessing;
	private MetaMapApiConnector metaMapApi;

	public void init() {

		tokenizer = new CoreNLPTokenizer();
		preprocessing = new TextPreprocess();
		try {
			preprocessing.loadStopword();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			loadTrainDtatList();

			initCategoryDictionary();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		metaMapApi = new MetaMapApiConnector("202.30.23.64");
		metaMapApi.Connection();

	}

	private void loadTrainDtatList() throws IOException {

		for (int i = 0; i < 3; i++) {
			ArrayList<String> trainingdata = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(new File(i
					+ "_TrainData.txt")));
			String line;
			while ((line = br.readLine()) != null) {

				trainingdata.add(line.trim());
			}

			TraingDataHashMap.put(i, trainingdata);
			br.close();
		}

	}

	private void initCategoryDictionary() throws IOException {

		for (int i = 0; i < 3; i++) {

			HashMap<String, Double> dictionary = new HashMap<String, Double>();
			CSVReader reader = new CSVReader(new FileReader(dicPath[i]));
			String[] nextLine;
			int num = 0;
			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				if (num > 0) {
					String word = nextLine[0];
					double score = Double.parseDouble(nextLine[1]);
					dictionary.put(word, score);

				}
				num++;
			}

			CategoryDictionaryHashMap.put(i, dictionary);
			reader.close();

		}

	}

	public void createTrainFile() throws IOException {

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				"svmTrainingNoDuplicate.txt")));
		for (int i = 0; i < 3; i++) {

			int size = TraingDataHashMap.get(i).size();

			for (int index = 0; index < size; index++) {
				switch (i) {
				case 0:
					bw.write("-1 ");
					break;
				case 1:

					bw.write("0 ");
					break;
				case 2:
					bw.write("+1 ");
					break;

				default:
					break;
				}
				double scoreSum[] = new double[3];
				String pmcid = TraingDataHashMap.get(i).get(index);
				System.out.println(pmcid + " " + pmcid.length());
				ArticleBody articleBody = articleBodyRepostory
						.findByPmcid(pmcid);
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

						for (int categorynum = 0; categorynum < 3; categorynum++) {
							if (CategoryDictionaryHashMap.get(categorynum)
									.containsKey(token)) {
								scoreSum[categorynum] += CategoryDictionaryHashMap
										.get(categorynum).get(token);
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
										paragraphs = paragraphs.replaceAll(
												match, " ");
										String match2 = "\\s{2,}";
										paragraphs = paragraphs.replaceAll(
												match2, " ");
										System.out.println(paragraphs);
										List<String> paragraphsResult = tokenizer
												.lemmatokenize(paragraphs);

										for (String token : paragraphsResult) {
											token = token.toLowerCase();

											for (int categorynum = 0; categorynum < 3; categorynum++) {
												if (CategoryDictionaryHashMap
														.get(categorynum)
														.containsKey(token)) {
													scoreSum[categorynum] += CategoryDictionaryHashMap
															.get(categorynum)
															.get(token);
												}
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
								for (Section section : articleBody
										.getSectionList()) {
									String text = section.getParagraphs();
									// System.out.println(text);
									String paragraphs = section.getParagraphs();
									if (paragraphs != null) {
										String match = "[^a-zA-Z]";
										paragraphs = paragraphs.replaceAll(
												match, " ");
										String match2 = "\\s{2,}";
										paragraphs = paragraphs.replaceAll(
												match2, " ");
										System.out.println(paragraphs);
										List<String> paragraphsResult = tokenizer
												.lemmatokenize(paragraphs);

										for (String token : paragraphsResult) {
											token = token.toLowerCase();

											for (int categorynum = 0; categorynum < 3; categorynum++) {
												if (CategoryDictionaryHashMap
														.get(categorynum)
														.containsKey(token)) {
													scoreSum[categorynum] += CategoryDictionaryHashMap
															.get(categorynum)
															.get(token);
												}
											}

										}
									}

								}
							}

						}

					}

				}

				bw.write("1:" + scoreSum[0] + " 2:" + scoreSum[1] + " 3:"
						+ scoreSum[2]);
				bw.newLine();
				bw.flush();
				System.out.println(i + "끝");
			}
		}

		bw.close();

	}

	public void createMetaTrainFile() throws Exception {

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				"svmTrainingMetamap.txt")));
		for (int i = 0; i < 3; i++) {

			int size = TraingDataHashMap.get(i).size();

			for (int index = 0; index < size; index++) {
				switch (i) {
				case 0:
					bw.write("-1 ");
					break;
				case 1:

					bw.write("0 ");
					break;
				case 2:
					bw.write("+1 ");
					break;

				default:
					break;
				}
				double scoreSum[] = new double[3];
				String pmcid = TraingDataHashMap.get(i).get(index);
				System.out.println(pmcid + " " + pmcid.length());
				ArticleBody articleBody = articleBodyRepostory
						.findByPmcid(pmcid);
				ArticleAbstract articleAbstract = articleAbstractRepository
						.findByPmcid(pmcid);

				if (articleBody != null) {
					String title = articleBody.getTitle();

					String resultString = title
							.replaceAll("[^\\x00-\\x7F]", "");
					List<Result> resultList = metaMapApi
							.SendTextMetaMap(resultString);
					List<String> titleresult = new ArrayList<String>();
					if(resultList.size()>0)
					{
					titleresult = metaMapApi.AnalzeMappingMetaMap3(resultList,
							pmcid);
					}
					for (String token : titleresult) {
						token = token.toLowerCase();

						for (int categorynum = 0; categorynum < 3; categorynum++) {
							if (CategoryDictionaryHashMap.get(categorynum)
									.containsKey(token)) {
								scoreSum[categorynum] += CategoryDictionaryHashMap
										.get(categorynum).get(token);
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
										String resultString = paragraphs
												.replaceAll("[^\\x00-\\x7F]",
														"");
										List<Result> resultList = metaMapApi
												.SendTextMetaMap(resultString);
										List<String> result = new ArrayList<String>();
										if(resultList.size()>0)
										{
										result = metaMapApi
												.AnalzeMappingMetaMap3(
														resultList, pmcid);
										}

										for (String token : result) {
											token = token.toLowerCase();

											for (int categorynum = 0; categorynum < 3; categorynum++) {
												if (CategoryDictionaryHashMap
														.get(categorynum)
														.containsKey(token)) {
													scoreSum[categorynum] += CategoryDictionaryHashMap
															.get(categorynum)
															.get(token);
												}
											}
										}
									}

								}
							}

						}

					}

				}

				bw.write("1:" + scoreSum[0] + " 2:" + scoreSum[1] + " 3:"
						+ scoreSum[2]);
				bw.newLine();
				bw.flush();
				System.out.println(i + "끝");
			}
		}

		bw.close();

	}

}
