package com.IndexServer.Bean;

import java.util.ArrayList;

public class evaluationContent {

	private String keyword = null;
	private int documentNum = 0;
	private ArrayList<String> ContentId = new ArrayList<String>();
	
	
	
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getDocumentNum() {
		return documentNum;
	}
	public void setDocumentNum(int documentNum) {
		this.documentNum = documentNum;
	}
	public ArrayList<String> getContentId() {
		return ContentId;
	}
	public void setContentId(ArrayList<String> contentId) {
		ContentId = contentId;
	}
	
	
	
	
	
}
