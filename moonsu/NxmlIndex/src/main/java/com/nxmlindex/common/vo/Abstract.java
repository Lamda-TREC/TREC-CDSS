/**
 * 
 */
package com.nxmlindex.common.vo;

import java.util.List;

/**
 * <pre>
 * com.nxmlindex.common
 *   |_ Abstract.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 5. 11. 오후 9:00:24
 * @Version : 
 */
public class Abstract {
	
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
