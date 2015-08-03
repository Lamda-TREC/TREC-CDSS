/**
 * 
 */
package com.nxmlindex.common.vo;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * <pre>
 * com.nxmlindex.common
 *   |_ ArticleContent.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 5. 11. 오후 6:48:20
 * @Version : 
 */
public class ArticleContent {
	
	
	private List<Section> sectionList;

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
