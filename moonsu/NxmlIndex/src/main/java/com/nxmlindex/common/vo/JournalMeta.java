/**
 * 
 */
package com.nxmlindex.common.vo;

import java.util.List;

/**
 * <pre>
 * com.nxmlindex.common
 *   |_ Journal.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 5. 11. 오후 6:44:30
 * @Version : 
 */
public class JournalMeta {
	
    private List<String> issn;
    private List<String> journalTitle;
    
    
	/**
	 * @return the issn
	 */
	public List<String> getIssn() {
		return issn;
	}
	/**
	 * @param issn the issn to set
	 */
	public void setIssn(List<String> issn) {
		this.issn = issn;
	}
	/**
	 * @return the journalTitle
	 */
	public List<String> getJournalTitle() {
		return journalTitle;
	}
	/**
	 * @param journalTitle the journalTitle to set
	 */
	public void setJournalTitle(List<String> journalTitle) {
		this.journalTitle = journalTitle;
	}
    
    
	
	
    
    

    
    

}
