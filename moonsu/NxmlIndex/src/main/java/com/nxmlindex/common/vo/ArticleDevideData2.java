/**
 * 
 */
package com.nxmlindex.common.vo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <pre>
 * com.nxmlindex.common.vo
 *   |_ ArticleSmallData.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 8. 오후 9:18:57
 * @Version : 
 */
@Document(collection="ArticleDevideData2")
public class ArticleDevideData2 {
	
	@Id
	private String id;
	
	private String pmcid;
	private String title;
	private List<Section> abstrctSectionList;
	private List<Section> bodySectionList;
	
	
	/**
	 * Desc : Constructor of ArticleSmallData.java class
	 */
	
	public ArticleDevideData2() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * Desc : Constructor of ArticleSmallData.java class
	 * @param pmcid
	 * @param publicationDate
	 * @param title
	 * @param abstrctSectionList
	 * @param bodySectionList
	 */
	
	public ArticleDevideData2(String pmcid, 
			String title, List<Section> abstrctSectionList,
			List<Section> bodySectionList) {
		super();
		this.pmcid = pmcid;
		this.title = title;
		this.abstrctSectionList = abstrctSectionList;
		this.bodySectionList = bodySectionList;
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
	 * @return the abstrctSectionList
	 */
	public List<Section> getAbstrctSectionList() {
		return abstrctSectionList;
	}


	/**
	 * @param abstrctSectionList the abstrctSectionList to set
	 */
	public void setAbstrctSectionList(List<Section> abstrctSectionList) {
		this.abstrctSectionList = abstrctSectionList;
	}


	/**
	 * @return the bodySectionList
	 */
	public List<Section> getBodySectionList() {
		return bodySectionList;
	}


	/**
	 * @param bodySectionList the bodySectionList to set
	 */
	public void setBodySectionList(List<Section> bodySectionList) {
		this.bodySectionList = bodySectionList;
	}
	
	
	
	 
	

}
