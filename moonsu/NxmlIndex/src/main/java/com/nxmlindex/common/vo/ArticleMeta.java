package com.nxmlindex.common.vo;


import java.util.List;

import org.springframework.data.annotation.Id;

public class ArticleMeta {
	

	
	private String title;
    private String pmid;
    private String pmcid;
    private Abstract abstractText;
    private JournalMeta journal;
    private PublicationDate publicationDate;
    
    
    
    
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
	 * @return the pmid
	 */
	public String getPmid() {
		return pmid;
	}
	/**
	 * @param pmid the pmid to set
	 */
	public void setPmid(String pmid) {
		this.pmid = pmid;
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
	 * @return the journal
	 */
	public JournalMeta getJournal() {
		return journal;
	}
	/**
	 * @param journal the journal to set
	 */
	public void setJournal(JournalMeta journal) {
		this.journal = journal;
	}
	/**
	 * @return the publicationDate
	 */
	public PublicationDate getPublicationDate() {
		return publicationDate;
	}
	/**
	 * @param publicationDate the publicationDate to set
	 */
	public void setPublicationDate(PublicationDate publicationDate) {
		this.publicationDate = publicationDate;
	}
	/**
	 * @return the abstractText
	 */
	public Abstract getAbstractText() {
		return abstractText;
	}
	/**
	 * @param abstractText the abstractText to set
	 */
	public void setAbstractText(Abstract abstractText) {
		this.abstractText = abstractText;
	}
	
   
    
    

}
