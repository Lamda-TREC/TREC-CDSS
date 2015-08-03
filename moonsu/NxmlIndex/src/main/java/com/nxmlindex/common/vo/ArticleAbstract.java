/**
 * 
 */
package com.nxmlindex.common.vo;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * <pre>
 * com.nxmlindex.common.vo
 *   |_ ArticleAbstract.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 6. 10. 오후 10:44:28
 * @Version : 
 */
public class ArticleAbstract {
	
	 @Id
	 private String id;
	 
	 private String pmcid;
	 private String title;
	 private List<Section> sectionList;
	 
	 
	/**
	 * Desc : Constructor of ArticleAbstract.java class
	 */
	
	public ArticleAbstract() {
		super();
		// TODO Auto-generated constructor stub
	}




	
	
	/**
	 * Desc : Constructor of ArticleAbstract.java class
	 * @param pmcid
	 * @param title
	 * @param sectionList
	 */
	
	public ArticleAbstract(String pmcid, String title, List<Section> sectionList) {
		super();
		this.pmcid = pmcid;
		this.title = title;
		this.sectionList = sectionList;
	}






	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * @return the pmcid
	 */
	public String getPmcid() {
		return pmcid;
	}
	/**
	 * @param pmcid the pmcid to set
	 */
	public void setPmcid(String pmcid) {
		this.pmcid = pmcid;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}






	/**
	 * @return the sectionList
	 */
	public List<Section> getSectionList() {
		return sectionList;
	}






	/**
	 * @param sectionList the sectionList to set
	 */
	public void setSectionList(List<Section> sectionList) {
		this.sectionList = sectionList;
	}

	
	 

}
