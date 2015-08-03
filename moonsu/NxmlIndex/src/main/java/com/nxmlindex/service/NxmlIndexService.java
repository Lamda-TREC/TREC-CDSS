/**
 * 
 */
package com.nxmlindex.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.nxmlindex.common.vo.ArticleAbstract;
import com.nxmlindex.common.vo.ArticleBody;
import com.nxmlindex.common.vo.Section;
import com.nxmlindex.mongodb.ArticleAbstractRepository;
import com.nxmlindex.mongodb.ArticleBodyRepository;
import com.nxmlindex.nlptools.OpenNLPTokenizer;
import com.nxmlindex.nlptools.TFIDF;
import com.nxmlindex.nlptools.TextPreprocess;
import com.nxmlindex.util.NxmlListLoader;

/**
 * <pre>
 * com.nxmlindex.service
 *   |_ NxmlIndexService.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 5. 오전 11:35:47
 * @Version : 
 */
public class NxmlIndexService {
	
	
	/*@Autowired
	private ArticleAbstractRepository repositoryAbstrct;
	
	@Autowired
	private ArticleBodyRepository repositoryBody;*/
	
	private NxmlListLoader nxmlListLoader = new NxmlListLoader();
	private ArrayList<String> nxmlList = null;
	private OpenNLPTokenizer tokenizer = new OpenNLPTokenizer();
	private TextPreprocess preprocess = new TextPreprocess();
	private TFIDF tfidf = new TFIDF();
	
	public void init()
	{
		try {
			tokenizer.initOpenNLP();
			preprocess.loadStopword();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadNxmlList(){
		
		try {
			nxmlListLoader.loadNxmlList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nxmlList = nxmlListLoader.getNxmlList();
		
	}
	
	
	
	
	/*
	private ArticleAbstract findNxmlAbstrct(String pmcid)
	{
		ArticleAbstract article= null;
		
		article = repositoryAbstrct.findByPmcid(pmcid);
		
		return article;
	}
	
	private ArticleBody findNxmlBody(String pmcid)
	{
		ArticleBody article= null;
		
		article = repositoryBody.findByPmcid(pmcid);
		
		return article;
	}*/




	/**
	 * @return the nxmlList
	 */
	public ArrayList<String> getNxmlList() {
		return nxmlList;
	}
	
	
	
	



	
	public void CalculateIDF(ArticleBody article){
		
		
		//ArticleBody article = findNxmlBody(pmcid);
			
		if (article != null)
		{
			for(Section section : article.getSectionList())
			{
				String tokens[] = tokenizer.tokenizeText(section.getParagraphs());
				
				for(String word : tokens)
				{
					tfidf.addIDFWord(word);
				}
				
			}
		}
	}

	/**
	 * @return the tfidf
	 */
	public TFIDF getTfidf() {
		return tfidf;
	}


	
	
	
	

}
