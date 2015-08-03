/**
 * 
 */
package com.nxmlindex.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.nxmlindex.common.vo.Article;
import com.nxmlindex.common.vo.ArticleAbstract;
import com.nxmlindex.common.vo.ArticleAbstractExist;
import com.nxmlindex.common.vo.ArticleBody;
import com.nxmlindex.common.vo.ArticleBodyExist;
import com.nxmlindex.common.vo.ArticleContent;
import com.nxmlindex.common.vo.ArticleMeta;
import com.nxmlindex.mongodb.ArticleAbstractExistRepository;
import com.nxmlindex.mongodb.ArticleAbstractRepository;
import com.nxmlindex.mongodb.ArticleBodyExistRepository;
import com.nxmlindex.mongodb.ArticleBodyRepository;
import com.nxmlindex.mongodb.ArticleRepository;
import com.nxmlindex.util.NxmlParser;

/**
 * <pre>
 * com.nxmlindex.service
 *   |_ NxmlInsertService.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 5. 오전 11:36:52
 * @Version : 
 */
public class NxmlInsertService {

	/*@Autowired
	private ArticleRepository repository;

	@Autowired
	private ArticleAbstractRepository repositoryAbstrct;

	@Autowired
	private ArticleBodyRepository repositoryBody;
	
	@Autowired
	private ArticleAbstractExistRepository repositoryAbstrctExist;
	
	@Autowired
	private ArticleBodyExistRepository repositoryBodyExist;

	
	
	public void NxmlInsert() throws IOException, ParserConfigurationException, XPathExpressionException{
		repository.deleteAll();
		repositoryAbstrct.deleteAll();
		repositoryBody.deleteAll();
		repositoryAbstrctExist.deleteAll();
		repositoryBodyExist.deleteAll();
		for (int i = 0; i < 4; i++) {
			FilesScannerService nxml = new FilesScannerService();
			switch (i) {
			case 0:
				nxml.FilesScanNxml("00");
				break;
			case 1:

				nxml.FilesScanNxml("01");
				break;
			case 2:

				nxml.FilesScanNxml("02");
				break;
			case 3:

				nxml.FilesScanNxml("03");
				break;
			}
			
			BlockingQueue<String> NxmlFilesQueue = nxml.getNxmlFilesQueue();
		
			int num = 0;
			for (String path : NxmlFilesQueue) {
				num++;
				
				 * if (num == 9134) { System.out.println(path); NxmlParser
				 * nxmlparser = new NxmlParser(); nxmlparser.readNxml(path);
				 * 
				 * ArticleMeta articleMeta = nxmlparser.parseFrontData();
				 * ArticleContent articleContent = nxmlparser.parseBodyData();
				 * //
				 * System.out.println(article.getAbstractText().getSectionList
				 * ().
				 * get(0).getTitle()+" : "+article.getAbstractText().getSectionList
				 * ().get(0).getParagraphs());
				 * 
				 * Article article = new Article(articleMeta, articleContent);
				 * jsonconverter
				 * .ObjectToJson(article.getArticleMeta().getPmcid(), article);
				 * }
				 

				NxmlParser nxmlparser = new NxmlParser();
				nxmlparser.readNxml(path);

				ArticleMeta articleMeta = nxmlparser.parseFrontData();
				ArticleContent articleContent = nxmlparser.parseBodyData();

				Article article = new Article(articleMeta, articleContent);

				ArticleAbstract articleAbstract = new ArticleAbstract(article
						.getArticleMeta().getPmcid(), article.getArticleMeta()
						.getTitle(), article.getArticleMeta().getAbstractText().getSectionList());
				
				ArticleAbstractExist articleAbstractExist = new ArticleAbstractExist(article
						.getArticleMeta().getPmcid(), article.getArticleMeta()
						.getTitle(), article.getArticleMeta().getAbstractText().getSectionList());
				

				ArticleBody articleBody = new ArticleBody(article
						.getArticleMeta().getPmcid(), article.getArticleMeta()
						.getTitle(), article.getArticleContent()
						.getSectionList());
				
				ArticleBodyExist articleBodyExist = new ArticleBodyExist(article
						.getArticleMeta().getPmcid(), article.getArticleMeta()
						.getTitle(), article.getArticleContent()
						.getSectionList());

				
				
				repository.save(article);
				repositoryAbstrct.save(articleAbstract);
				repositoryBody.save(articleBody);
				if(articleAbstractExist.getSectionList().size() !=0)
				{
					repositoryAbstrctExist.save(articleAbstractExist);
				}
				else
				{

					BufferedWriter br = new BufferedWriter(new FileWriter(new File("D:\\문수\\DATA\\TRECDATA\\NoAbstractPmcid.txt"),true));
					br.write(articleAbstractExist.getPmcid());
					br.newLine();
					br.flush();
					br.close();
				}
				
				if(articleBodyExist.getSectionList().size() !=0)
				{
					repositoryBodyExist.save(articleBodyExist);
				}else
				{
					BufferedWriter br = new BufferedWriter(new FileWriter(new File("D:\\문수\\DATA\\TRECDATA\\NoBodyPmcid.txt"),true));
					br.write(articleBodyExist.getPmcid());
					br.newLine();
					br.flush();
					br.close();
				}

				System.out.println("success : " + num);
			}
		}
	}*/
	
}
