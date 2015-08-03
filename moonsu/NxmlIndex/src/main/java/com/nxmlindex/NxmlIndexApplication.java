package com.nxmlindex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.nxmlindex.service.SVMDictionaryService;
import com.nxmlindex.service.SVMTrainFileCreator;

@SpringBootApplication
@ComponentScan
public class NxmlIndexApplication implements CommandLineRunner {

	/*
	 * @Autowired private ArticleBodyRepository repositoryBody;
	 */

	/*@Autowired
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
	private ArticleDevideData3Repository articleDevide3DataRepository;*/
	
	@Autowired
	SVMDictionaryService svmDictionaryServie;
	
	@Autowired
	SVMTrainFileCreator svm;
	
	public static HashMap<Integer, HashMap<String, Integer>> CategoryHashMap = new HashMap<Integer, HashMap<String,Integer>>();

	public static HashMap<String, Integer> DiaHashMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> TestHashMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> TreatHashMap = new HashMap<String, Integer>();
	
	public static HashMap<String, Double> DiaTFHashMap = new HashMap<String, Double>();
	public static HashMap<String, Double> TestTFHashMap = new HashMap<String, Double>();
	public static HashMap<String, Double> TreatTFHashMap = new HashMap<String, Double>();
	
	public static HashMap<String, Integer> DFHashMap = new HashMap<String, Integer>();
	public static HashMap<String, Double> IDFHashMap = new HashMap<String, Double>();
	
	public static HashMap<String, Double> DiaTFIDFHashMap = new HashMap<String, Double>();
	public static HashMap<String, Double> TestTFIDFHashMap = new HashMap<String, Double>();
	public static HashMap<String, Double> TreatTFIDFHashMap = new HashMap<String, Double>();
	
	public static HashMap<String, Double> DiaNoDuplicateTFIDFHashMap = new HashMap<String, Double>();
	public static HashMap<String, Double> TestNoDuplicateTFIDFHashMap = new HashMap<String, Double>();
	public static HashMap<String, Double> TreatNoDuplicateTFIDFHashMap = new HashMap<String, Double>();
	
	public static HashMap<String, HashMap<Integer, Double>> TotalTFIDFHashMap = new HashMap<String, HashMap<Integer,Double>>();
	
	
	public static HashMap<String, HashMap<String, Integer>> DocumentHashMap = new HashMap<String, HashMap<String,Integer>>();
	
	public static HashMap<String, HashMap<String, Double>> DocumentTFHashMap = new HashMap<String, HashMap<String,Double>>();
	
	public static HashMap<String, HashMap<String, Double>> TotalDocumentTFIDFHashMap = new HashMap<String, HashMap<String,Double>>();
	
	
	
	public static HashMap<String, Double> AverageTotalDocumentTFIDFHashMap = new HashMap<String, Double>();
	
	
	public static void main(String[] args) {
		SpringApplication.run(NxmlIndexApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
	/*	CategoryHashMap.put(0, DiaHashMap);
		CategoryHashMap.put(1, TestHashMap);
		CategoryHashMap.put(2, TreatHashMap);
		
		
		
		svmDictionaryServie.init();
		
		svmDictionaryServie.constructDictionary();*/
		
		
		
		svm.init();
		svm.createMetaTrainFile();
		
	 
		
	/*	FilesScannerService scanner = new FilesScannerService();

		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				scanner.FilesScanNxml("00");
				break;
			case 1:
				scanner.FilesScanNxml("01");
				break;
			case 2:
				scanner.FilesScanNxml("02");
				break;
			case 3:
				scanner.FilesScanNxml("03");
				break;

			default:
				break;
			}
		}
		
	
	DevideData dvide = new DevideData();
	// dvide.devideNxml();

	int num = dvide.pmcList.size();

	ArrayList<Integer> randomNumber = dvide.randomSelector(num);

	System.out.println("저장 시작");
	for (int i = 0; i < 250000; i++) {
		int index = randomNumber.get(i);
		String pmcid = dvide.pmcList.get(index);
		System.out.println(pmcid + " " + pmcid.length());
		ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
		ArticleAbstract articleAbstract = articleAbstractRepository
				.findByPmcid(pmcid);
		ArticleDevideData1 aritcleDevideData1 = null;
		
		if (articleBody != null && articleAbstract != null) {
			if (articleBody.getSectionList() == null
					&& articleAbstract.getSectionList() == null) {
				aritcleDevideData1 = new ArticleDevideData1(pmcid,
						articleAbstract.getTitle(),
						new ArrayList<Section>(), new ArrayList<Section>());

			} else if (articleBody.getSectionList() != null&&articleAbstract.getSectionList() != null) {
				
				aritcleDevideData1 = new ArticleDevideData1(pmcid,
						articleAbstract.getTitle(),
						articleAbstract.getSectionList(),
						articleBody.getSectionList());

			

			} else if (articleAbstract.getSectionList() == null) {
				aritcleDevideData1 = new ArticleDevideData1(pmcid,
						articleAbstract.getTitle(),
						new ArrayList<Section>(),
						articleBody.getSectionList());

			} else if(articleBody.getSectionList() == null){
				
				aritcleDevideData1 = new ArticleDevideData1(pmcid,
						articleAbstract.getTitle(),
						articleAbstract.getSectionList(),
						new ArrayList<Section>());
			}

			articleDevide1DataRepository.save(aritcleDevideData1);
			dvide.savePmcList.add(pmcid);
		}
		

	}

	System.out.println("저장1 끝");

	for (int i = 250000; i < 500000; i++) {
		int index = randomNumber.get(i);
		String pmcid = dvide.pmcList.get(index);
		System.out.println(pmcid + " " + pmcid.length());
		ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
		ArticleAbstract articleAbstract = articleAbstractRepository
				.findByPmcid(pmcid);
		ArticleDevideData2 aritcleDevideData2 = null;
		
		if (articleBody != null && articleAbstract != null) {
			if (articleBody.getSectionList() == null
					&& articleAbstract.getSectionList() == null) {
				aritcleDevideData2 = new ArticleDevideData2(pmcid,
						articleAbstract.getTitle(),
						new ArrayList<Section>(), new ArrayList<Section>());

			} else if (articleBody.getSectionList() != null&&articleAbstract.getSectionList() != null) {
				
				aritcleDevideData2 = new ArticleDevideData2(pmcid,
						articleAbstract.getTitle(),
						articleAbstract.getSectionList(),
						articleBody.getSectionList());

			

			} else if (articleAbstract.getSectionList() == null) {
				aritcleDevideData2 = new ArticleDevideData2(pmcid,
						articleAbstract.getTitle(),
						new ArrayList<Section>(),
						articleBody.getSectionList());

			} else if(articleBody.getSectionList() == null){
				
				aritcleDevideData2 = new ArticleDevideData2(pmcid,
						articleAbstract.getTitle(),
						articleAbstract.getSectionList(),
						new ArrayList<Section>());
			}

			articleDevide2DataRepository.save(aritcleDevideData2);
			dvide.savePmcList.add(pmcid);
		}
		
		

	}
	
	System.out.println("저장2 끝");
	
	for (int i = 500000; i < dvide.pmcList.size(); i++) {
		int index = randomNumber.get(i);
		String pmcid = dvide.pmcList.get(index);
		System.out.println(pmcid + " " + pmcid.length());
		ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
		ArticleAbstract articleAbstract = articleAbstractRepository
				.findByPmcid(pmcid);
		ArticleDevideData3 aritcleDevideData3 = null;
		
		if (articleBody != null && articleAbstract != null) {
			if (articleBody.getSectionList() == null
					&& articleAbstract.getSectionList() == null) {
				aritcleDevideData3 = new ArticleDevideData3(pmcid,
						articleAbstract.getTitle(),
						new ArrayList<Section>(), new ArrayList<Section>());

			} else if (articleBody.getSectionList() != null&&articleAbstract.getSectionList() != null) {
				
				aritcleDevideData3 = new ArticleDevideData3(pmcid,
						articleAbstract.getTitle(),
						articleAbstract.getSectionList(),
						articleBody.getSectionList());

			

			} else if (articleAbstract.getSectionList() == null) {
				aritcleDevideData3 = new ArticleDevideData3(pmcid,
						articleAbstract.getTitle(),
						new ArrayList<Section>(),
						articleBody.getSectionList());

			} else if(articleBody.getSectionList() == null){
				
				aritcleDevideData3 = new ArticleDevideData3(pmcid,
						articleAbstract.getTitle(),
						articleAbstract.getSectionList(),
						new ArrayList<Section>());
			}

			articleDevide3DataRepository.save(aritcleDevideData3);
			dvide.savePmcList.add(pmcid);
		}

	}
	

	System.out.println("저장3  끝");
	*/
	/*
	
		DevideData dvide = new DevideData();

		//ArrayList<Integer> topicNumber[] = new ArrayList[30];

		nxmlListLoader.loadNxmlList();
		HashMap<Integer, String> answerNxml = nxmlListLoader
				.getNxmlAnswerHashMap();
		
		String diaType = "dia";
		String testType = "test";
		String treatType = "treat";
		
		MetaMapApiConnector metaMapApi = new MetaMapApiConnector("202.30.23.64");
		metaMapApi.Connection();
		
		ArrayList<Integer> topicNumber[] = new ArrayList[3];
		topicNumber[0] = dvide.randomSelector(0, 1119);
		topicNumber[1] = dvide.randomSelector(1119, 2134);
		topicNumber[2] = dvide.randomSelector(2134, 3356);
		
		TextPreprocess preprocessing = new TextPreprocess();
		preprocessing.loadStopword();
		
		for(int i=0;i<500;i++)
		{
			
		
			
			
			int index = topicNumber[0].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());

			HashMap<String, Integer> metaMapHashMap = new HashMap<String, Integer>();
			NxmlIndexApplication.TotalMetaMapHashMap.put(pmcid, metaMapHashMap);
			
			HashMap<String,Double> metaMapTFHashMap = new HashMap<String, Double>();
			NxmlIndexApplication.TotalMetaMapTFHashMap.put(pmcid, metaMapTFHashMap);
			
			HashMap<String, Double> metaMapTFIDFHashMap = new HashMap<String, Double>();
			
			NxmlIndexApplication.TotalMetaMapTFIDFHashMap.put(pmcid, metaMapTFIDFHashMap);
			
					
			
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			if(articleBody != null)
			{
				String title = articleBody.getTitle();
				
				String resultString = title.replaceAll("[^\\x00-\\x7F]", "");
				List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
				
				if(resultList.size()>0)
					metaMapApi.AnalzeMappingMetaMap2(resultList,pmcid);
			}
			
			System.out.println("본문 분석 시작");
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				}else {
					
					
					if (articleAbstract.getSectionList() != null) {
						
						System.out.println("sectionList size : "+articleAbstract.getSectionList().size());
						if(articleAbstract.getSectionList().size()<50)
						{
						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							//System.out.println(text);
							String resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
							
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap2(resultList,pmcid);
						}
						}

					}
					
					
					

				}
				

				
			}
			System.out.println(i+"끝");
			
			
		}
		
		TFIDF2 tfidf2 = new TFIDF2();
		
		tfidf2.calculateTF();
		tfidf2.calculateIDF();
		tfidf2.calculateTFIDF();
		tfidf2.averageTFIDF();
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				diaType+"_MetaMapDocument_TFIDF.csv")));
		bw.write("Semantic Type");
		
		
		
		Iterator<String> keys = NxmlIndexApplication.AverageTotalMetaMapTFIDFHashMap.keySet().iterator();
		
		

		while (keys.hasNext()) {
			
			String key = keys.next();
			
			double tfidfvalue = NxmlIndexApplication.AverageTotalMetaMapTFIDFHashMap.get(key);
			bw.write(key + "," + tfidfvalue);
			bw.newLine();

			
		}
		

		bw.flush();
		bw.close();
		
		NxmlIndexApplication.TotalMetaMapHashMap.clear();
		NxmlIndexApplication.TotalMetaMapTFHashMap.clear();
		NxmlIndexApplication.TotalMetaMapTFIDFHashMap.clear();
		NxmlIndexApplication.AverageTotalMetaMapTFIDFHashMap.clear();
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[1].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			
			HashMap<String, Integer> metaMapHashMap = new HashMap<String, Integer>();
			NxmlIndexApplication.TotalMetaMapHashMap.put(pmcid, metaMapHashMap);
			
			HashMap<String,Double> metaMapTFHashMap = new HashMap<String, Double>();
			NxmlIndexApplication.TotalMetaMapTFHashMap.put(pmcid, metaMapTFHashMap);
			
			HashMap<String, Double> metaMapTFIDFHashMap = new HashMap<String, Double>();
			
			NxmlIndexApplication.TotalMetaMapTFIDFHashMap.put(pmcid, metaMapTFIDFHashMap);
			
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			if(articleBody != null)
			{
				String title = articleBody.getTitle();
				
				String resultString = title.replaceAll("[^\\x00-\\x7F]", "");
				List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
				
				if(resultList.size()>0)
					metaMapApi.AnalzeMappingMetaMap2(resultList,pmcid);
			}
			
			
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				} else {
					
					if (articleAbstract.getSectionList() != null) {
						System.out.println("sectionList size : "+articleAbstract.getSectionList().size());
						if(articleAbstract.getSectionList().size()<50)
						{
						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							//System.out.println(text);
							
							String resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap2(resultList,pmcid);
						}
						}

					}
					
					
					
					

				}

				
			}
			System.out.println(i+"끝");
			
		}
		
		
		tfidf2 = new TFIDF2();
		
		tfidf2.calculateTF();
		tfidf2.calculateIDF();
		tfidf2.calculateTFIDF();
		tfidf2.averageTFIDF();
		
		
		bw = new BufferedWriter(new FileWriter(new File(
				testType+"_MetaMapDocument_TFIDF.csv")));
		bw.write("Semantic Type");
		
		
		
		keys = NxmlIndexApplication.AverageTotalMetaMapTFIDFHashMap.keySet().iterator();
		
		

		while (keys.hasNext()) {
			
			String key = keys.next();
			
			double tfidfvalue = NxmlIndexApplication.AverageTotalMetaMapTFIDFHashMap.get(key);
			bw.write(key + "," + tfidfvalue);
			bw.newLine();

			
		}
		

		bw.flush();
		bw.close();
		

		NxmlIndexApplication.TotalMetaMapHashMap.clear();
		NxmlIndexApplication.TotalMetaMapTFHashMap.clear();
		NxmlIndexApplication.TotalMetaMapTFIDFHashMap.clear();
		NxmlIndexApplication.AverageTotalMetaMapTFIDFHashMap.clear();
		
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[2].get(i);
			String pmcid = answerNxml.get(index);
			
			HashMap<String, Integer> metaMapHashMap = new HashMap<String, Integer>();
			NxmlIndexApplication.TotalMetaMapHashMap.put(pmcid, metaMapHashMap);
			
			HashMap<String,Double> metaMapTFHashMap = new HashMap<String, Double>();
			NxmlIndexApplication.TotalMetaMapTFHashMap.put(pmcid, metaMapTFHashMap);
			
			HashMap<String, Double> metaMapTFIDFHashMap = new HashMap<String, Double>();
			NxmlIndexApplication.TotalMetaMapTFIDFHashMap.put(pmcid, metaMapTFIDFHashMap);
			
			System.out.println(pmcid + " " + pmcid.length());
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			if(articleBody != null)
			{
				String title = articleBody.getTitle();
				
				String resultString = title.replaceAll("[^\\x00-\\x7F]", "");
				List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
				
				if(resultList.size()>0)
					metaMapApi.AnalzeMappingMetaMap2(resultList,pmcid);
			}
			
			
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				} else {
					
					if (articleAbstract.getSectionList() != null) {
						System.out.println("sectionList size : "+articleAbstract.getSectionList().size());
						if(articleAbstract.getSectionList().size()<50)
						{
						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							//System.out.println(text);
							String resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap2(resultList,pmcid);
						}
						}
					}
					
				
					
					

				}

				
			}
			System.out.println(i+"끝");
		}
		
		
		tfidf2 = new TFIDF2();
		
		tfidf2.calculateTF();
		tfidf2.calculateIDF();
		tfidf2.calculateTFIDF();
		tfidf2.averageTFIDF();
		
		
		bw = new BufferedWriter(new FileWriter(new File(
				treatType+"_MetaMapDocument_TFIDF.csv")));
		bw.write("Semantic Type");
		
		
		
		keys = NxmlIndexApplication.AverageTotalMetaMapTFIDFHashMap.keySet().iterator();
		
		

		while (keys.hasNext()) {
			
			String key = keys.next();
			
			double tfidfvalue = NxmlIndexApplication.AverageTotalMetaMapTFIDFHashMap.get(key);
			bw.write(key + "," + tfidfvalue);
			bw.newLine();

			
		}
		

		bw.flush();
		bw.close();
		

		NxmlIndexApplication.TotalMetaMapHashMap.clear();
		NxmlIndexApplication.TotalMetaMapTFHashMap.clear();
		NxmlIndexApplication.TotalMetaMapTFIDFHashMap.clear();
		NxmlIndexApplication.AverageTotalMetaMapTFIDFHashMap.clear();
		
		*/
		
		/*for(String stopword : preprocessing.getStopword())
		{
			NxmlIndexApplication.DiaMetaMapHashMap.remove(stopword);
			NxmlIndexApplication.TestMetaMapHashMap.remove(stopword);
			NxmlIndexApplication.TreatMetaMapHashMap.remove(stopword);
		}
		
		
		System.out.println("TFIDF 시작");
		TFIDF tfidf = new TFIDF();
		tfidf.calculateTF();
		tfidf.calculateIDF();
		tfidf.calculateTFIDF();
		
		
		bw = new BufferedWriter(new FileWriter(new File(
				testType+"_MetaMapBody_TFIDF.csv")));
		bw.write("Semantic Type,TFIDF");
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
		
		bw = new BufferedWriter(new FileWriter(new File(
				treatType+"_MetaMapBody_TFIDF.csv")));
		bw.write("Semantic Type,TFIDF");
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
		
		
		bw = new BufferedWriter(new FileWriter(new File(
				"Total_MetaMapBody_TFIDF.csv")));
		bw.write("Semantic Type,DiagTFIDF,TestTFIDf,TreatTFIDF");
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
		bw.close();*/
		
		/*NxmlAsnwerParser nxmlAnswerParser = new NxmlAsnwerParser();
		nxmlAnswerParser.nxmlAnswerParse();
		
		int size = nxmlAnswerParser.getNxmlAnswer().size();
		for(int index = 0; index < size;index++)
		{
			NxmlAnswer nxmlAnswer =  nxmlAnswerParser.getNxmlAnswer().get(index);
			
			String pmcid = nxmlAnswer.getPmcid();
			String topicid = nxmlAnswer.getTopicid();
			String relevancetype = nxmlAnswer.getRelevancetype();
			
			ArticleBody articleBody = articleBodyRepostory
					.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			ArticleAnswerData aritcleAnswerData = null;

			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					aritcleAnswerData = new ArticleAnswerData(pmcid,
							articleAbstract.getTitle(),
							new ArrayList<Section>(), new ArrayList<Section>(),topicid,relevancetype);

				} else if (articleBody.getSectionList() != null&&articleAbstract.getSectionList() != null) {
					
					aritcleAnswerData = new ArticleAnswerData(pmcid,
							articleAbstract.getTitle(),
							articleAbstract.getSectionList(),
							articleBody.getSectionList(),topicid,relevancetype);

				

				} else if (articleAbstract.getSectionList() == null) {
					aritcleAnswerData = new ArticleAnswerData(pmcid,
							articleAbstract.getTitle(),
							new ArrayList<Section>(),
							articleBody.getSectionList(),topicid,relevancetype);

				} else if(articleBody.getSectionList() == null){
					
					aritcleAnswerData = new ArticleAnswerData(pmcid,
							articleAbstract.getTitle(),
							articleAbstract.getSectionList(),
							new ArrayList<Section>(),topicid,relevancetype);
				}
				
				
				articleAnswerDataRepository.save(aritcleAnswerData);
			}
				
				
		}
*/		
		
		
	/*	NxmlListLoader nxmlListLoader = new NxmlListLoader();
		
		DevideData dvide = new DevideData();

		//ArrayList<Integer> topicNumber[] = new ArrayList[30];

		nxmlListLoader.loadNxmlList();
		HashMap<Integer, String> answerNxml = nxmlListLoader
				.getNxmlAnswerHashMap();
		
		String diaType = "dia";
		String testType = "test";
		String treatType = "treat";
		
		MetaMapApiConnector metaMapApi = new MetaMapApiConnector("202.30.23.64");
		metaMapApi.Connection();
		
		ArrayList<Integer> topicNumber[] = new ArrayList[3];
		topicNumber[0] = dvide.randomSelector(0, 1119);
		topicNumber[1] = dvide.randomSelector(1119, 2134);
		topicNumber[2] = dvide.randomSelector(2134, 3356);
		
		TextPreprocess preprocessing = new TextPreprocess();
		preprocessing.loadStopword();
		
		for(int i=0;i<500;i++)
		{
			
			HashMap<String, Integer> metaMapHashMap = new HashMap<String, Integer>();
			
			
			int index = topicNumber[0].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			NxmlIndexApplication.TotalMetaMapHashMap.put(pmcid, metaMapHashMap);
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			if(articleBody != null)
			{
				String title = articleBody.getTitle();
				
				String resultString = title.replaceAll("[^\\x00-\\x7F]", "");
				List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
				
				if(resultList.size()>0)
					metaMapApi.AnalzeMappingMetaMap(resultList,0);
			}
			
			System.out.println("본문 분석 시작");
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				}else {
					
					
					if (articleBody.getSectionList() != null) {
						
						System.out.println("sectionList size : "+articleBody.getSectionList().size());
						if(articleBody.getSectionList().size()<50)
						{
						for(Section section : articleBody.getSectionList())		
						{
							String text = section.getParagraphs();
							//System.out.println(text);
							String resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
							
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap(resultList, 0);
						}
						}

					}
					
					
					

				}
				

				
			}
			System.out.println(i+"끝");
			
			
		}
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[1].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			if(articleBody != null)
			{
				String title = articleBody.getTitle();
				
				String resultString = title.replaceAll("[^\\x00-\\x7F]", "");
				List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
				
				if(resultList.size()>0)
					metaMapApi.AnalzeMappingMetaMap(resultList,1);
			}
			
			
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				} else {
					
					if (articleBody.getSectionList() != null) {
						System.out.println("sectionList size : "+articleBody.getSectionList().size());
						if(articleBody.getSectionList().size()<50)
						{
						for(Section section : articleBody.getSectionList())		
						{
							String text = section.getParagraphs();
							//System.out.println(text);
							
							String resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap(resultList, 1);
						}
						}

					}
					
					
					
					

				}

				
			}
			System.out.println(i+"끝");
			
		}
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[2].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			if(articleBody != null)
			{
				String title = articleBody.getTitle();
				
				String resultString = title.replaceAll("[^\\x00-\\x7F]", "");
				List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
				
				if(resultList.size()>0)
					metaMapApi.AnalzeMappingMetaMap(resultList,2);
			}
			
			
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				} else {
					
					if (articleBody.getSectionList() != null) {
						System.out.println("sectionList size : "+articleBody.getSectionList().size());
						if(articleBody.getSectionList().size()<50)
						{
						for(Section section : articleBody.getSectionList())		
						{
							String text = section.getParagraphs();
							//System.out.println(text);
							String resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap(resultList, 2);
						}
						}
					}
					
				
					
					

				}

				
			}
			System.out.println(i+"끝");
		}
		
		
		
		for(String stopword : preprocessing.getStopword())
		{
			NxmlIndexApplication.DiaMetaMapHashMap.remove(stopword);
			NxmlIndexApplication.TestMetaMapHashMap.remove(stopword);
			NxmlIndexApplication.TreatMetaMapHashMap.remove(stopword);
		}
		
		
		System.out.println("TFIDF 시작");
		TFIDF tfidf = new TFIDF();
		tfidf.calculateTF();
		tfidf.calculateIDF();
		tfidf.calculateTFIDF();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				diaType+"_MetaMapBody_TFIDF.csv")));
		bw.write("Semantic Type,TFIDF");
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
		
		bw = new BufferedWriter(new FileWriter(new File(
				testType+"_MetaMapBody_TFIDF.csv")));
		bw.write("Semantic Type,TFIDF");
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
		
		bw = new BufferedWriter(new FileWriter(new File(
				treatType+"_MetaMapBody_TFIDF.csv")));
		bw.write("Semantic Type,TFIDF");
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
		
		
		bw = new BufferedWriter(new FileWriter(new File(
				"Total_MetaMapBody_TFIDF.csv")));
		bw.write("Semantic Type,DiagTFIDF,TestTFIDf,TreatTFIDF");
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
		bw.close();*/
		
		
		
		/*
		NxmlListLoader nxmlListLoader = new NxmlListLoader();
		
		DevideData dvide = new DevideData();

		//ArrayList<Integer> topicNumber[] = new ArrayList[30];

		nxmlListLoader.loadNxmlList();
		HashMap<Integer, String> answerNxml = nxmlListLoader
				.getNxmlAnswerHashMap();
		
		String diaType = "dia";
		String testType = "test";
		String treatType = "treat";
		
		MetaMapApiConnector metaMapApi = new MetaMapApiConnector("202.30.23.64");
		metaMapApi.Connection();
		
		ArrayList<Integer> topicNumber[] = new ArrayList[3];
		topicNumber[0] = dvide.randomSelector(0, 13138);
		topicNumber[1] = dvide.randomSelector(13138, 26500);
		topicNumber[2] = dvide.randomSelector(26500, 37949);
		
		TextPreprocess preprocessing = new TextPreprocess();
		preprocessing.loadStopword();
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[0].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			if(articleBody != null)
			{
				String title = articleBody.getTitle();
				
				String resultString = title.replaceAll("[^\\x00-\\x7F]", "");
				List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
				
				if(resultList.size()>0)
					metaMapApi.AnalzeMappingMetaMap(resultList,0);
			}
			
			System.out.println("본문 분석 시작");
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				}else {
					
					
					if (articleAbstract.getSectionList() != null) {
						
						System.out.println("sectionList size : "+articleAbstract.getSectionList().size());
						if(articleAbstract.getSectionList().size()<50)
						{
						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							System.out.println(text);
							String resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
							
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap(resultList, 0);
						}
						}

					}
					
					if (articleBody.getSectionList() != null) {
						
						System.out.println("sectionList size : "+articleBody.getSectionList().size());
						if(articleBody.getSectionList().size()<50)
						{
						for(Section section : articleBody.getSectionList())		
						{
							String paragraphs = section.getParagraphs();
							String split[] = paragraphs.split("\\.");
							for(String text : split)
							{
							System.out.println(text);
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap(resultList, 0);
							}
						}
						}

					}
					
					
					if(articleBody.getSectionList() != null && articleAbstract.getSectionList() != null){
			
						

						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 0);
						}

						
						
						for(Section section : articleBody.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 0);
						}
					}	
					else if (articleBody.getSectionList() == null) {
						
						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 0);
						}

					} else if (articleAbstract.getSectionList() == null) {
						
						for(Section section : articleBody.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 0);
						}
						

					}
					
					

				}
				

				
			}
			System.out.println(i+"끝");
			
			
		}
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[1].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			if(articleBody != null)
			{
				String title = articleBody.getTitle();
				
				String resultString = title.replaceAll("[^\\x00-\\x7F]", "");
				List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
				
				if(resultList.size()>0)
					metaMapApi.AnalzeMappingMetaMap(resultList,1);
			}
			
			
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				} else {
					
					if (articleAbstract.getSectionList() != null) {
						System.out.println("sectionList size : "+articleAbstract.getSectionList().size());
						if(articleAbstract.getSectionList().size()<50)
						{
						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							System.out.println(text);
							
							String resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap(resultList, 1);
						}
						}

					}
					
					if (articleBody.getSectionList() != null) {
						
						System.out.println("sectionList size : "+articleBody.getSectionList().size());
						if(articleBody.getSectionList().size()<50)
						{
						for(Section section : articleBody.getSectionList())		
						{
							String paragraphs = section.getParagraphs();
							String split[] = paragraphs.split("\\.");
							for(String text : split)
							{
							System.out.println(text);
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap(resultList, 1);
							}
						}
						}

					}
					
					if(articleBody.getSectionList() != null && articleAbstract.getSectionList() != null){
						

						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList,1);
						}

						
						
						for(Section section : articleBody.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 1);
						}
					}	
					else if (articleBody.getSectionList() == null) {
						
						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 1);
						}

					} else if (articleAbstract.getSectionList() == null) {
						
						for(Section section : articleBody.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList,1);
						}
						

					}
					
					

				}

				
			}
			System.out.println(i+"끝");
			
		}
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[2].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			if(articleBody != null)
			{
				String title = articleBody.getTitle();
				
				String resultString = title.replaceAll("[^\\x00-\\x7F]", "");
				List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
				
				if(resultList.size()>0)
					metaMapApi.AnalzeMappingMetaMap(resultList,2);
			}
			
			
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				} else {
					
					if (articleAbstract.getSectionList() != null) {
						System.out.println("sectionList size : "+articleAbstract.getSectionList().size());
						if(articleAbstract.getSectionList().size()<50)
						{
						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							System.out.println(text);
							String resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							List<Result> resultList= metaMapApi.SendTextMetaMap(resultString);
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap(resultList, 2);
						}
						}
					}
					
					if (articleBody.getSectionList() != null) {
						
						System.out.println("sectionList size : "+articleBody.getSectionList().size());
						if(articleBody.getSectionList().size()<50)
						{
						for(Section section : articleBody.getSectionList())		
						{
							String paragraphs = section.getParagraphs();
							String split[] = paragraphs.split("\\.");
							for(String text : split)
							{
							System.out.println(text);
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							if(resultList.size()>0)
								metaMapApi.AnalzeMappingMetaMap(resultList, 2);
							}
						}
						}

					}
					
					if(articleBody.getSectionList() != null && articleAbstract.getSectionList() != null){
						

						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 2);
						}

						
						
						for(Section section : articleBody.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 2);
						}
					}	
					else if (articleBody.getSectionList() == null) {
						
						for(Section section : articleAbstract.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 2);
						}

					} else if (articleAbstract.getSectionList() == null) {
						
						for(Section section : articleBody.getSectionList())		
						{
							String text = section.getParagraphs();
							resultString = text.replaceAll("[^\\x00-\\x7F]", "");
							resultList= metaMapApi.SendTextMetaMap(resultString);
							
							metaMapApi.AnalzeMappingMetaMap(resultList, 2);
						}
						

					}
					
					

				}

				
			}
			System.out.println(i+"끝");
		}
		
		
		
		for(String stopword : preprocessing.getStopword())
		{
			NxmlIndexApplication.DiaMetaMapHashMap.remove(stopword);
			NxmlIndexApplication.TestMetaMapHashMap.remove(stopword);
			NxmlIndexApplication.TreatMetaMapHashMap.remove(stopword);
		}
		
		
		System.out.println("TFIDF 시작");
		TFIDF tfidf = new TFIDF();
		tfidf.calculateTF();
		tfidf.calculateIDF();
		tfidf.calculateTFIDF();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				diaType+"_MetaMapWord_TFIDF.csv")));
		bw.write("Semantic Word,TFIDF");
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
		
		bw = new BufferedWriter(new FileWriter(new File(
				testType+"_MetaMapWord_TFIDF.csv")));
		bw.write("Semantic Word,TFIDF");
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
		
		bw = new BufferedWriter(new FileWriter(new File(
				treatType+"_MetaMapWord_TFIDF.csv")));
		bw.write("Semantic Word,TFIDF");
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
		
		
		bw = new BufferedWriter(new FileWriter(new File(
				"Total_MetaMapWord_TFIDF.csv")));
		bw.write("Semantic Word,DiagTFIDF,TestTFIDf,TreatTFIDF");
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
		bw.close();*/
		
	/*	NxmlListLoader nxmlListLoader = new NxmlListLoader();
		
		DevideData dvide = new DevideData();

		//ArrayList<Integer> topicNumber[] = new ArrayList[30];

		nxmlListLoader.loadNxmlList();
		HashMap<Integer, String> answerNxml = nxmlListLoader
				.getNxmlAnswerHashMap();
		
		String diaType = "dia";
		String testType = "test";
		String treatType = "treat";
		
		ArrayList<Integer> topicNumber[] = new ArrayList[3];
		topicNumber[0] = dvide.randomSelector(0, 13138);
		topicNumber[1] = dvide.randomSelector(13138, 26500);
		topicNumber[2] = dvide.randomSelector(26500, 37949);
		
		CoreNLPTokenizer tokenizer = new CoreNLPTokenizer();
		TextPreprocess preprocessing = new TextPreprocess();
		preprocessing.loadStopword();
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[0].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			String title = articleBody.getTitle();
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			title =title.replaceAll(match, "");
			List<String> titleresult = tokenizer.lemmatokenize(title);
			
			for(String token : titleresult)
			{
				token = token.toLowerCase();
				if(NxmlIndexApplication.DiaMetaMapHashMap.size() == 0)
				{
					NxmlIndexApplication.DiaMetaMapHashMap.put(token, 1);
				}else
				{
					if(NxmlIndexApplication.DiaMetaMapHashMap.containsKey(token))
					{
						int count = NxmlIndexApplication.DiaMetaMapHashMap.get(token);
						count++;
						NxmlIndexApplication.DiaMetaMapHashMap.put(token, count);
						
					}else
					{
						NxmlIndexApplication.DiaMetaMapHashMap.put(token, 1);
					}
				}
			}
			
			System.out.println("본문 분석 시작");
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				}else {
					
					
				
					
					if (articleBody.getSectionList() != null) {
						
						System.out.println("sectionList size : "+articleBody.getSectionList().size());
						if(articleBody.getSectionList().size()<50)
						{
						for(Section section : articleBody.getSectionList())		
						{
							String paragraphs = section.getParagraphs();
							if(paragraphs !=null)
							{
							match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
							paragraphs =paragraphs.replaceAll(match, "");
							titleresult = tokenizer.lemmatokenize(paragraphs);
							
							for(String token : titleresult)
							{
								token = token.toLowerCase();
								
									if(NxmlIndexApplication.DiaMetaMapHashMap.containsKey(token))
									{
										int count = NxmlIndexApplication.DiaMetaMapHashMap.get(token);
										count++;
										NxmlIndexApplication.DiaMetaMapHashMap.put(token, count);
										
									}else
									{
										NxmlIndexApplication.DiaMetaMapHashMap.put(token, 1);
									}
								
							}
							}
							
						}
						}

					}
					
			

				}
				

				
			}
			System.out.println(i+"끝");
			
			
		}
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[1].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			String title = articleBody.getTitle();
			
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			title =title.replaceAll(match, "");
			List<String> titleresult = tokenizer.lemmatokenize(title);
			
			for(String token : titleresult)
			{
				token = token.toLowerCase();
				if(NxmlIndexApplication.TestMetaMapHashMap.size() == 0)
				{
					NxmlIndexApplication.TestMetaMapHashMap.put(token, 1);
				}else
				{
					if(NxmlIndexApplication.TestMetaMapHashMap.containsKey(token))
					{
						int count = NxmlIndexApplication.TestMetaMapHashMap.get(token);
						count++;
						NxmlIndexApplication.TestMetaMapHashMap.put(token, count);
						
					}else
					{
						NxmlIndexApplication.TestMetaMapHashMap.put(token, 1);
					}
				}
			}
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				} else {
					
					
					
					if (articleBody.getSectionList() != null) {
						
						System.out.println("sectionList size : "+articleBody.getSectionList().size());
						if(articleBody.getSectionList().size()<50)
						{
						for(Section section : articleBody.getSectionList())		
						{
							String paragraphs = section.getParagraphs();
							
							if(paragraphs !=null)
							{
							match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
							paragraphs =paragraphs.replaceAll(match, "");
							titleresult = tokenizer.lemmatokenize(paragraphs);
							
							for(String token : titleresult)
							{
								token = token.toLowerCase();
								
									if(NxmlIndexApplication.TestMetaMapHashMap.containsKey(token))
									{
										int count = NxmlIndexApplication.TestMetaMapHashMap.get(token);
										count++;
										NxmlIndexApplication.TestMetaMapHashMap.put(token, count);
										
									}else
									{
										NxmlIndexApplication.TestMetaMapHashMap.put(token, 1);
									}
								
							}
							}
						}
						}

					}
					

				}

				
			}
			System.out.println(i+"끝");
			
		}
		
		for(int i=0;i<500;i++)
		{
			int index = topicNumber[2].get(i);
			String pmcid = answerNxml.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			
			String title = articleBody.getTitle();
			
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			title =title.replaceAll(match, "");
			List<String> titleresult = tokenizer.lemmatokenize(title);
			
			for(String token : titleresult)
			{
				token = token.toLowerCase();
				if(NxmlIndexApplication.TreatMetaMapHashMap.size() == 0)
				{
					NxmlIndexApplication.TreatMetaMapHashMap.put(token, 1);
				}else
				{
					if(NxmlIndexApplication.TreatMetaMapHashMap.containsKey(token))
					{
						int count = NxmlIndexApplication.TreatMetaMapHashMap.get(token);
						count++;
						NxmlIndexApplication.TreatMetaMapHashMap.put(token, count);
						
					}else
					{
						NxmlIndexApplication.TreatMetaMapHashMap.put(token, 1);
					}
				}
			}
			
			
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					

				} else {
					
					
					
					if (articleBody.getSectionList() != null) {
						
						System.out.println("sectionList size : "+articleBody.getSectionList().size());
						if(articleBody.getSectionList().size()<50)
						{
						for(Section section : articleBody.getSectionList())		
						{
							String paragraphs = section.getParagraphs();
							if(paragraphs !=null)
							{
							match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
							paragraphs =paragraphs.replaceAll(match, "");
							titleresult = tokenizer.lemmatokenize(paragraphs);
							
							for(String token : titleresult)
							{
								token = token.toLowerCase();
								
									if(NxmlIndexApplication.TreatMetaMapHashMap.containsKey(token))
									{
										int count = NxmlIndexApplication.TreatMetaMapHashMap.get(token);
										count++;
										NxmlIndexApplication.TreatMetaMapHashMap.put(token, count);
										
									}else
									{
										NxmlIndexApplication.TreatMetaMapHashMap.put(token, 1);
									}
								
							}
							}
						}
						}

					}
			
					

				}

				
			}
			System.out.println(i+"끝");
		}
		
		
		
		for(String stopword : preprocessing.getStopword())
		{
			NxmlIndexApplication.DiaMetaMapHashMap.remove(stopword);
			NxmlIndexApplication.TestMetaMapHashMap.remove(stopword);
			NxmlIndexApplication.TreatMetaMapHashMap.remove(stopword);
		}
		
		
		
		
		
		System.out.println("TFIDF 시작");
		TFIDF tfidf = new TFIDF();
		tfidf.calculateTF();
		tfidf.calculateIDF();
		tfidf.calculateTFIDF();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
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
		
		bw = new BufferedWriter(new FileWriter(new File(
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
		
		bw = new BufferedWriter(new FileWriter(new File(
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
		
		
		bw = new BufferedWriter(new FileWriter(new File(
				"Total_Word_TFIDF.csv")));
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
		bw.close();*/
		

		/*FilesScannerService scanner = new FilesScannerService();

		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				scanner.FilesScanNxml("00");
				break;
			case 1:
				scanner.FilesScanNxml("01");
				break;
			case 2:
				scanner.FilesScanNxml("02");
				break;
			case 3:
				scanner.FilesScanNxml("03");
				break;

			default:
				break;
			}
		}

		DevideData dvide = new DevideData();
		// dvide.devideNxml();

		int num = dvide.pmcList.size();

		ArrayList<Integer> randomNumber = dvide.randomSelector(num);

		System.out.println("저장 시작");
		for (int i = 0; i < 75000; i++) {
			int index = randomNumber.get(i);
			String pmcid = dvide.pmcList.get(index);
			System.out.println(pmcid + " " + pmcid.length());
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository
					.findByPmcid(pmcid);
			ArticleSmallData aritcleSmallData = null;
			
			if (articleBody != null && articleAbstract != null) {
				if (articleBody.getSectionList() == null
						&& articleAbstract.getSectionList() == null) {
					aritcleSmallData = new ArticleSmallData(pmcid,
							articleAbstract.getTitle(),
							new ArrayList<Section>(), new ArrayList<Section>());

				} else if (articleBody.getSectionList() != null&&articleAbstract.getSectionList() != null) {
					
					aritcleSmallData = new ArticleSmallData(pmcid,
							articleAbstract.getTitle(),
							articleAbstract.getSectionList(),
							articleBody.getSectionList());

				

				} else if (articleAbstract.getSectionList() == null) {
					aritcleSmallData = new ArticleSmallData(pmcid,
							articleAbstract.getTitle(),
							new ArrayList<Section>(),
							articleBody.getSectionList());

				} else if(articleBody.getSectionList() == null){
					
					aritcleSmallData = new ArticleSmallData(pmcid,
							articleAbstract.getTitle(),
							articleAbstract.getSectionList(),
							new ArrayList<Section>());
				}

				articleSmallDataRepository.save(aritcleSmallData);
				dvide.savePmcList.add(pmcid);
			}
			

		}

		System.out.println("저장 끝");
		System.out.println("정답 저장 시작");
		NxmlListLoader nxmlListLoader = new NxmlListLoader();

		

		nxmlListLoader.loadNxmlList();
		
		
		for(String pmcid : nxmlListLoader.getNxmlList())
		{
			if (!dvide.savePmcList.contains(pmcid)) {
				ArticleBody articleBody = articleBodyRepostory
						.findByPmcid(pmcid);
				ArticleAbstract articleAbstract = articleAbstractRepository
						.findByPmcid(pmcid);
				ArticleSmallData aritcleSmallData = null;

				if (articleBody != null && articleAbstract != null) {
					if (articleBody.getSectionList() == null
							&& articleAbstract.getSectionList() == null) {
						aritcleSmallData = new ArticleSmallData(pmcid,
								articleAbstract.getTitle(),
								new ArrayList<Section>(), new ArrayList<Section>());

					} else if (articleBody.getSectionList() != null&&articleAbstract.getSectionList() != null) {
						
						aritcleSmallData = new ArticleSmallData(pmcid,
								articleAbstract.getTitle(),
								articleAbstract.getSectionList(),
								articleBody.getSectionList());

					

					} else if (articleAbstract.getSectionList() == null) {
						aritcleSmallData = new ArticleSmallData(pmcid,
								articleAbstract.getTitle(),
								new ArrayList<Section>(),
								articleBody.getSectionList());

					} else if(articleBody.getSectionList() == null){
						
						aritcleSmallData = new ArticleSmallData(pmcid,
								articleAbstract.getTitle(),
								articleAbstract.getSectionList(),
								new ArrayList<Section>());
					}


					articleSmallDataRepository.save(aritcleSmallData);

				}
			}
		}
		*/
		
		/*HashMap<Integer, String> answerNxml = nxmlListLoader
				.getNxmlAnswerHashMap();
		ArrayList<Integer> topicNumber[] = new ArrayList[30];
		topicNumber[0] = dvide.randomSelector(0, 1463);
		topicNumber[1] = dvide.randomSelector(1463, 2740);
		topicNumber[2] = dvide.randomSelector(2740, 4204);
		topicNumber[3] = dvide.randomSelector(4204, 5353);
		topicNumber[4] = dvide.randomSelector(5353, 6847);
		topicNumber[5] = dvide.randomSelector(6847, 8384);
		topicNumber[6] = dvide.randomSelector(8384, 9440);
		topicNumber[7] = dvide.randomSelector(9440, 10676);
		topicNumber[8] = dvide.randomSelector(10676, 12086);
		topicNumber[9] = dvide.randomSelector(12086, 13138);
		topicNumber[10] = dvide.randomSelector(13138, 14729);
		topicNumber[11] = dvide.randomSelector(14729, 16206);
		topicNumber[12] = dvide.randomSelector(16206, 17652);
		topicNumber[13] = dvide.randomSelector(17652, 19321);
		topicNumber[14] = dvide.randomSelector(19321, 20400);
		topicNumber[15] = dvide.randomSelector(20400, 21666);
		topicNumber[16] = dvide.randomSelector(21666, 22778);
		topicNumber[17] = dvide.randomSelector(22778, 24015);
		topicNumber[18] = dvide.randomSelector(24015, 25222);
		topicNumber[19] = dvide.randomSelector(25222, 26500);
		topicNumber[20] = dvide.randomSelector(26500, 27667);
		topicNumber[21] = dvide.randomSelector(27667, 28588);
		topicNumber[22] = dvide.randomSelector(28588, 29828);
		topicNumber[23] = dvide.randomSelector(29828, 30888);
		topicNumber[24] = dvide.randomSelector(30888, 32343);
		topicNumber[25] = dvide.randomSelector(32343, 33403);
		topicNumber[26] = dvide.randomSelector(33403, 34311);
		topicNumber[27] = dvide.randomSelector(34311, 35445);
		topicNumber[28] = dvide.randomSelector(35445, 36636);
		topicNumber[29] = dvide.randomSelector(36636, 37949);

		BufferedWriter bw = new BufferedWriter(new FileWriter(
				"testDataAnswer.txt"));
		bw.write("topicNum\tPmcid");
		bw.newLine();
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 100; j++) {
				int index = topicNumber[i].get(j);
				String pmcid = answerNxml.get(index);
				bw.write(i + "\t" + pmcid);
				bw.newLine();
				if (!dvide.savePmcList.contains(pmcid)) {
					ArticleBody articleBody = articleBodyRepostory
							.findByPmcid(pmcid);
					ArticleAbstract articleAbstract = articleAbstractRepository
							.findByPmcid(pmcid);
					ArticleSmallData aritcleSmallData = null;

					if (articleBody != null && articleAbstract != null) {
						if (articleBody.getSectionList() == null
								&& articleAbstract.getSectionList() == null) {
							aritcleSmallData = new ArticleSmallData(pmcid,
									articleAbstract.getTitle(),
									new ArrayList<Section>(), new ArrayList<Section>());

						} else if (articleBody.getSectionList() != null&&articleAbstract.getSectionList() != null) {
							
							aritcleSmallData = new ArticleSmallData(pmcid,
									articleAbstract.getTitle(),
									articleAbstract.getSectionList(),
									articleBody.getSectionList());

						

						} else if (articleAbstract.getSectionList() == null) {
							aritcleSmallData = new ArticleSmallData(pmcid,
									articleAbstract.getTitle(),
									new ArrayList<Section>(),
									articleBody.getSectionList());

						} else if(articleBody.getSectionList() == null){
							
							aritcleSmallData = new ArticleSmallData(pmcid,
									articleAbstract.getTitle(),
									articleAbstract.getSectionList(),
									new ArrayList<Section>());
						}


						articleSmallDataRepository.save(aritcleSmallData);

					}
				}
			}
		}

		bw.flush();
		bw.close();*/
	//	System.out.println("정답 저장 끝");

		/*
		 * ArticleBody articlebody = repositoryBody.findByPmcid("1033958");
		 * 
		 * System.out.println(articlebody.getPmcid());
		 */
		/*
		 * NxmlIndexService index = new NxmlIndexService();
		 * 
		 * index.init(); System.out.println("index init 완료");
		 * index.loadNxmlList(); System.out.println("index loadnxml 완료");
		 * for(String pmcid : index.getNxmlList()) { System.out.println(pmcid
		 * +" 시작"); ArticleBody articlebody = repositoryBody.findByPmcid(pmcid);
		 * index.CalculateIDF(articlebody);
		 * System.out.println(index.getTfidf().getIDF().size() + " 개수"); }
		 * 
		 * System.out.println(index.getTfidf().getIDF().get("cancer"));
		 */

		/*
		 * System.out.println(article.getAbstractText().getSectionList().get(0).
		 * getTitle()+" : "+article.getAbstractText().getSectionList().get(0).
		 * getParagraphList().get(0));
		 * System.out.println(articlecContent.getSectionList
		 * ().get(0).getTitle());
		 */

		/*
		 * System.out.println(ZonedDateTime.now().format(
		 * DateTimeFormatter.RFC_1123_DATE_TIME)); DocumentBuilderFactory
		 * factory = DocumentBuilderFactory.newInstance();
		 * 
		 * DocumentBuilder db = factory.newDocumentBuilder();
		 * 
		 * // parse file into DOM Document doc = db .parse(new File(
		 * "D:\\문수\\연구실\\프로젝트\\TRECCDSS\\pmcTextXML\\pmc-text-00.tar\\pmc-text-00\\01\\2649307.nxml"
		 * ));
		 * 
		 * XPath xpath = XPathFactory.newInstance().newXPath();
		 * 
		 * NodeList nodeList =
		 * (NodeList)xpath.evaluate("//front/journal-meta/issn",
		 * doc,XPathConstants.NODESET);
		 * 
		 * System.out.println(nodeList.getLength()); for(int
		 * i=0;i<nodeList.getLength();i++) {
		 * System.out.println(nodeList.item(i).
		 * getNodeName()+" : "+nodeList.item(i).getTextContent());
		 * 
		 * }
		 */

		/*
		 * NodeList bodyList = doc.getElementsByTagName("front"); Element body =
		 * (Element) bodyList.item(0); NodeList n1 =
		 * body.getElementsByTagName("journal-meta");
		 * 
		 * System.out.println(n1.getLength()); for (int j = 0; j <
		 * n1.getLength(); j++) { Node bookNode = n1.item(j); Element
		 * bookElement = (Element) bookNode; NodeList titleNode =
		 * bookElement.getElementsByTagName("journal-id");
		 * 
		 * for(int i =0 ; i <titleNode.getLength();i++) {
		 * 
		 * Node pNode = titleNode.item(i);
		 * System.out.println(pNode.getAttributes
		 * ().getNamedItem("journal-id-type").getNodeValue()); Element pElement
		 * = (Element) pNode;
		 * 
		 * System.out.println(pElement.getTagName() + " : " +
		 * pElement.getTextContent()); }
		 */
		// System.out.println(((Element)titleNode.item(0)).getTagName()+" "+((Element)titleNode.item(0)).getTextContent());

		/*
		 * NodeList n2 = body.getElementsByTagName("p");
		 * System.out.println(n2.getLength()); for (int i = 0; i <
		 * n2.getLength(); i++) { Node pNode = n2.item(i); Element pElement =
		 * (Element) pNode; System.out.println(pElement.getTagName() + " : " +
		 * pElement.getTextContent());
		 * 
		 * }
		 */
		// }

	}
	
	
	public static List sortByValue(final Map map) {
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
