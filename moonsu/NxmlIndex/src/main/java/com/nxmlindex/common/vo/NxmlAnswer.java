/**
 * 
 */
package com.nxmlindex.common.vo;

/**
 * <pre>
 * com.nxmlindex.common.vo
 *   |_ NxmlAnswer.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 16. 오전 1:39:16
 * @Version : 
 */
public class NxmlAnswer {

	
	private String topicid;
	
	private String pmcid;
	
	private String relevancetype;

	/**
	 * Desc : Constructor of NxmlAnswer.java class
	 * @param topicid
	 * @param pmcid
	 * @param relevancetype
	 */
	
	public NxmlAnswer(String topicid, String pmcid, String relevancetype) {
		super();
		this.topicid = topicid;
		this.pmcid = pmcid;
		this.relevancetype = relevancetype;
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
