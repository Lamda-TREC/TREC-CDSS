/**
 * 
 */
package com.nxmlindex.common.vo;

import org.springframework.data.annotation.Id;

/**
 * <pre>
 * com.nxmlindex.common.vo
 *   |_ Article.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 6. 10. 오전 12:15:07
 * @Version : 
 */
public class Article {

	  @Id
	  private String id;
	  
	  private ArticleMeta articleMeta;
	  private ArticleContent articleContent;
	  
	  /**
		 * Desc : Constructor of Article.java class
		 */
		
		public Article() {
			super();
			
		}
	  
	  
	/**
	 * Desc : Constructor of Article.java class
	 * @param articleMeta
	 * @param articleContent
	 */
	
	public Article(ArticleMeta articleMeta, ArticleContent articleContent) {
		super();
		this.articleMeta = articleMeta;
		this.articleContent = articleContent;
	}


	/**
	 * @return the articleMeta
	 */
	public ArticleMeta getArticleMeta() {
		return articleMeta;
	}


	/**
	 * @param articleMeta the articleMeta to set
	 */
	public void setArticleMeta(ArticleMeta articleMeta) {
		this.articleMeta = articleMeta;
	}


	/**
	 * @return the articleContent
	 */
	public ArticleContent getArticleContent() {
		return articleContent;
	}


	/**
	 * @param articleContent the articleContent to set
	 */
	public void setArticleContent(ArticleContent articleContent) {
		this.articleContent = articleContent;
	}
	  
	  
	  
	
	
}
