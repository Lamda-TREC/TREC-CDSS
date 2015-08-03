/**
 * 
 */
package com.nxmlindex.common.vo;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * <pre>
 * com.nxmlindex.common.vo
 *   |_ ArticleAnswerData.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 16. 오전 1:46:47
 * @Version : 
 */
public class ArticleAnswerData {

	@Id
	private String id;
	
	private String pmcid;
	private String title;
	private List<Section> abstrctSectionList;
	private List<Section> bodySectionList;
	private String topicid;
	private String relevancetype;
	
	
	/**
	 * Desc : Constructor of ArticleAnswerData.java class
	 * @param pmcid
	 * @param title
	 * @param abstrctSectionList
	 * @param bodySectionList
	 * @param topicid
	 * @param relevancetype
	 */
	
	public ArticleAnswerData(String pmcid, String title,
			List<Section> abstrctSectionList, List<Section> bodySectionList,
			String topicid, String relevancetype) {
		super();
		this.pmcid = pmcid;
		this.title = title;
		this.abstrctSectionList = abstrctSectionList;
		this.bodySectionList = bodySectionList;
		this.topicid = topicid;
		this.relevancetype = relevancetype;
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


	/**
	 * @return the topicid
	 */
	public String getTopicid() {
		return topicid;
	}


	/**
	 * @param topicid the topicid to set
	 */
	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}


	/**
	 * @return the relevancetype
	 */
	public String getRelevancetype() {
		return relevancetype;
	}


	/**
	 * @param relevancetype the relevancetype to set
	 */
	public void setRelevancetype(String relevancetype) {
		this.relevancetype = relevancetype;
	}
	
	
	
}
