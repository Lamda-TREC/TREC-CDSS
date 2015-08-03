/**
 * 
 */
package com.nxmlindex.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.nxmlindex.common.vo.Article;
import com.nxmlindex.common.vo.ArticleAbstract;
import com.nxmlindex.common.vo.ArticleBody;
import com.nxmlindex.common.vo.ArticleSmallData;
import com.nxmlindex.mongodb.ArticleAbstractRepository;
import com.nxmlindex.mongodb.ArticleBodyRepository;
import com.nxmlindex.mongodb.ArticleRepository;
import com.nxmlindex.mongodb.ArticleSmallDataRepository;

/**
 * <pre>
 * com.nxmlindex.util
 *   |_ DevideData.java
 * </pre>
 * 
 * Desc :
 * 
 * @Company : LAMDA in Ajou Univ
 * @Author : ChaMunsu
 * @Date : 2015. 7. 8. 오후 5:20:59
 * @Version :
 */
public class DevideData {

	public static ArrayList<String> pmcList = new ArrayList<String>();
	 
	public ArrayList<String> savePmcList = new ArrayList<String>();
	
	
	
	public ArrayList<Integer> randomSelector(int num) {

		ArrayList<Integer> ranNumber = new ArrayList<Integer>();

		for (int i = 0; i < num; i++) {

			ranNumber.add(i);

		}

		Collections.shuffle(ranNumber);
		Collections.shuffle(ranNumber);
		Collections.shuffle(ranNumber);
		
		return ranNumber;

	}
	
	public ArrayList<Integer> randomSelector(int start, int end) {

		ArrayList<Integer> ranNumber = new ArrayList<Integer>();

		for (int i = start; i < end; i++) {

			ranNumber.add(i);

		}

		Collections.shuffle(ranNumber);
		Collections.shuffle(ranNumber);
		Collections.shuffle(ranNumber);
		
		return ranNumber;

	}
	
	
	/*public void devideNxml() throws IOException{
		
		int num = pmcList.size();
		
		ArrayList<Integer> randomNumber = randomSelector(num);
		
		System.out.println("저장 시작");
		for(int i = 0; i< 150000;i++)
		{
			int index = randomNumber.get(i);
			String pmcid = pmcList.get(index);
			System.out.println(pmcid+" "+pmcid.length());
			ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
			ArticleAbstract articleAbstract = articleAbstractRepository.findByPmcid(pmcid);
			
			ArticleSmallData aritcleSmallData = new ArticleSmallData(pmcid, articleAbstract.getTitle(), articleAbstract.getSectionList(), articleBody.getSectionList());
			
			articleSmallDataRepository.save(aritcleSmallData);
			savePmcList.add(pmcid);
			
			
		}

		System.out.println("저장 끝");
		System.out.println("정답 저장 시작");
		NxmlListLoader nxmlListLoader = new NxmlListLoader();
		
		ArrayList<Integer> topicNumber[] = new ArrayList[30];
		
		nxmlListLoader.loadNxmlList();
		HashMap<Integer, String> answerNxml= nxmlListLoader.getNxmlAnswerHashMap();
		
		topicNumber[0] = randomSelector(0, 1463);
		topicNumber[1] = randomSelector(1463, 2740);
		topicNumber[2] = randomSelector(2740, 4204);
		topicNumber[3] = randomSelector(4204, 5353);
		topicNumber[4] = randomSelector(5353, 6847);
		topicNumber[5] = randomSelector(6847, 8384);
		topicNumber[6] = randomSelector(8384, 9440);
		topicNumber[7] = randomSelector(9440, 10676);
		topicNumber[8] = randomSelector(10676, 12086);
		topicNumber[9] = randomSelector(12086, 13138);
		topicNumber[10] = randomSelector(13138, 14729);
		topicNumber[11] = randomSelector(14729, 16206);
		topicNumber[12] = randomSelector(16206, 17652);
		topicNumber[13] = randomSelector(17652, 19321);
		topicNumber[14] = randomSelector(19321, 20400);
		topicNumber[15] = randomSelector(20400, 21666);
		topicNumber[16] = randomSelector(21666, 22778);
		topicNumber[17] = randomSelector(22778, 24015);
		topicNumber[18] = randomSelector(24015, 25222);
		topicNumber[19] = randomSelector(25222, 26500);
		topicNumber[20] = randomSelector(26500, 27667);
		topicNumber[21] = randomSelector(27667, 28588);
		topicNumber[22] = randomSelector(28588, 29828);
		topicNumber[23] = randomSelector(29828, 30888);
		topicNumber[24] = randomSelector(30888, 32343);
		topicNumber[25] = randomSelector(32343, 33403);
		topicNumber[26] = randomSelector(33403, 34311);
		topicNumber[27] = randomSelector(34311, 35445);
		topicNumber[28] = randomSelector(35445, 36636);
		topicNumber[29] = randomSelector(36636, 37949);
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("testDataAnswer.txt"));
		bw.write("topicNum\tPmcid");
		for(int i=0;i<30;i++)
		{
			for(int j = 0; j< 100;j++)
			{
				int index = topicNumber[i].get(j);
				String pmcid = answerNxml.get(index);
				bw.write(i+"\t"+pmcid);
				if(!savePmcList.contains(pmcid))
				{
					ArticleBody articleBody = articleBodyRepostory.findByPmcid(pmcid);
					ArticleAbstract articleAbstract = articleAbstractRepository.findByPmcid(pmcid);
					
					ArticleSmallData aritcleSmallData = new ArticleSmallData(pmcid, articleAbstract.getTitle(), articleAbstract.getSectionList(), articleBody.getSectionList());
					
					articleSmallDataRepository.save(aritcleSmallData);
				}
			}
		}
		
		bw.flush();
		bw.close();
		System.out.println("정답 저장 끝");
		
		
		
	}
	*/
	
	
	
	

}
