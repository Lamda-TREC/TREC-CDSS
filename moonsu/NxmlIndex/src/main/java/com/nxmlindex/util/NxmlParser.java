package com.nxmlindex.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.nxmlindex.common.vo.Abstract;
import com.nxmlindex.common.vo.ArticleContent;
import com.nxmlindex.common.vo.ArticleMeta;
import com.nxmlindex.common.vo.JournalMeta;
import com.nxmlindex.common.vo.PublicationDate;
import com.nxmlindex.common.vo.Section;

public class NxmlParser {

	

	private String nxmlPath;
	private boolean isRead = false;
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private XPath xpath;
	private Document nxml;

	private NodeList frontList;
	private Element front;

	/**
	 * Desc : Constructor of NxmlParser.java class
	 * 
	 * @throws ParserConfigurationException
	 */
	public NxmlParser() throws ParserConfigurationException {
		super();
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();
		xpath = XPathFactory.newInstance().newXPath();

	}

	/**
	 * @return the nxmlPath
	 */
	public String getNxmlPath() {
		return nxmlPath;
	}

	/**
	 * @param nxmlPath
	 *            the nxmlPath to set
	 */
	public void setNxmlPath(String nxmlPath) {
		this.nxmlPath = nxmlPath;
	}

	/**
	 * Desc : this Function read the NXML file.
	 * 
	 * @Method Name : readNxml
	 * @param nxmlPath
	 *            :nxmlPath is the Nxml file path.
	 * @return Return type is boolean. Read success is true. Read fail is false.
	 */
	public boolean readNxml(String nxmlPath) {

		this.nxmlPath = nxmlPath;
		try {
			nxml = docBuilder.parse(new File(nxmlPath));
			isRead = true;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("SAX Exception Error");
			isRead = false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			isRead = false;
			System.out.println("IO Exception Error");
			e.printStackTrace();
		}

		return isRead;
	}

	/**
	 * Desc : this function get nodeList by tagname in nxml.
	 * 
	 * @Method Name : getNodeListByTagNameInNxml
	 * @param tagName
	 *            : tagName is xml tag name that you want to find.
	 * @return return type is NodeList.
	 */
	private NodeList getNodeListByTagNameInNxml(String tagName) {

		NodeList nodeList = null;

		nodeList = nxml.getElementsByTagName(tagName);

		if (nodeList.getLength() == 0) {
			System.out.println("NodeList length is 0.");
		}

		return nodeList;
	}

	/**
	 * Desc : this function get nodeList by tagname in element.
	 * 
	 * @Method Name : getNodeListByTagNameInElement
	 * @param element
	 *            : element is element in nodeList or doc.
	 * @param tagName
	 *            : tagName is xml tag name that you want to find.
	 * @return return type is NodeList.
	 */
	private NodeList getNodeListByTagNameInElement(Element element,
			String tagName) {

		NodeList nodeList = null;

		nodeList = element.getElementsByTagName(tagName);

		if (nodeList.getLength() == 0) {
			System.out.println("NodeList length is 0.");
		}

		return nodeList;
	}

	/**
	 * Desc : this function get text in element.
	 * 
	 * @Method Name : getTextInElement
	 * @param element
	 *            : if you want to text in element, your input is element.
	 * @return : return is string.
	 */
	private String getTextInElement(Element element) {
		String text;

		text = element.getTextContent();

		return text;
	}

	public ArticleMeta parseFrontData() throws XPathExpressionException {

		ArticleMeta articleMeta= null;
		if (isRead) {

			JournalMeta journalMeta = findJournalMeta();
			
			articleMeta = findArticleMeta();
			
			articleMeta.setJournal(journalMeta);

			return articleMeta;

		} else {
			System.out.println("Nxml must read.");
		}

		return articleMeta;

	}
	
	
	private JournalMeta findJournalMeta() throws XPathExpressionException{
		
		JournalMeta journalMeta= new JournalMeta();
		List<String> issnList = new ArrayList<String>();
		List<String> journalTitleList = new ArrayList<String>();
		NodeList issnNodeList = (NodeList) xpath.evaluate(
				"//front/journal-meta/issn", nxml, XPathConstants.NODESET);
		

		if (issnNodeList.getLength() >= 1) {
			for (int index = 0; index < issnNodeList.getLength(); index++) {
					
				String issn = issnNodeList.item(index).getTextContent();
				issnList.add(issn);
				
			}
			
		}else
		{
			issnList.add("empty");
			journalMeta.setIssn(issnList);
			
		}
		
		NodeList journalTitleNodeList = (NodeList) xpath.evaluate(
				"//front/journal-meta/journal-title", nxml, XPathConstants.NODESET);
		
		if(journalTitleNodeList.getLength() >= 1) {
			for (int index = 0; index < journalTitleNodeList.getLength(); index++) {
				
				String journalTitle = journalTitleNodeList.item(index).getTextContent();
				issnList.add(journalTitle);
				
			}
		}else
		{

			journalTitleList.add("empty");
			journalMeta.setJournalTitle(journalTitleList);
		}
		
		return journalMeta;
		
	}
	
	private ArticleMeta findArticleMeta() throws XPathExpressionException
	{
		ArticleMeta articleMeta = new ArticleMeta();
		
		NodeList articlTilteNodeList = (NodeList) xpath.evaluate(
				"//front/article-meta/title-group/article-title", nxml, XPathConstants.NODESET);
		
		if(articlTilteNodeList.getLength() >= 1)
		{
			for (int index = 0; index < articlTilteNodeList.getLength(); index++) {
			
				String articleTitle = articlTilteNodeList.item(index).getTextContent();
				articleMeta.setTitle(articleTitle);
			}
		}else
		{
			articleMeta.setTitle("empty");
		}
		
		NodeList pmIdNodeList = (NodeList) xpath.evaluate(
				"//front/article-meta/article-id[@pub-id-type='pmid']", nxml, XPathConstants.NODESET);
		
		if(pmIdNodeList.getLength() >= 1)
		{
			for (int index = 0; index < pmIdNodeList.getLength(); index++) {
			
				String pmid = pmIdNodeList.item(index).getTextContent();
				articleMeta.setPmid(pmid);
			}
		}else
		{
			articleMeta.setPmid("empty");
		}
		
		NodeList pmcIdNodeList = (NodeList) xpath.evaluate(
				"//front/article-meta/article-id[@pub-id-type='pmc']", nxml, XPathConstants.NODESET);
		
		if(pmcIdNodeList.getLength() >= 1)
		{
			for (int index = 0; index < pmcIdNodeList.getLength(); index++) {
			
				String pmcid = pmcIdNodeList.item(index).getTextContent();
				articleMeta.setPmcid(pmcid);
			}
		}else
		{
			articleMeta.setPmcid("empty");
		}
		
		Abstract abstractText = new Abstract();
		List<Section> sectionList = new ArrayList<Section>();
		NodeList abstractNodeList = (NodeList) xpath.evaluate(
				"//front/article-meta/abstract/p", nxml, XPathConstants.NODESET);
		
		if(abstractNodeList.getLength() >= 1)
		{
			Section section = new Section();
			//List<String> paragraphList = new ArrayList<String>();
			StringBuilder paragraphBuilder = new StringBuilder();
			section.setTitle("Other");
			for (int index = 0; index < abstractNodeList.getLength(); index++) {
			
				 String paragraph = abstractNodeList.item(index).getTextContent();
				 //paragraphList.add(paragraph);
				 paragraphBuilder.append(paragraph);
			}
			//section.setParagraphList(paragraphList);
			section.setParagraphs(paragraphBuilder.toString());
			sectionList.add(section);
			
		}
		
		NodeList abstractSecNodeList = (NodeList) xpath.evaluate(
				"//front/article-meta/abstract//sec", nxml, XPathConstants.NODESET);
		
		if(abstractSecNodeList.getLength() >= 1)
		{
			
			for (int index = 0; index < abstractSecNodeList.getLength(); index++) {
				
				 Section section = new Section();
				 String title; 
				 //List<String> paragraphList = new ArrayList<String>();
				 StringBuilder paragraphBuilder = new StringBuilder();
				 
				 Element secElement =(Element)abstractSecNodeList.item(index);
				 NodeList titleNodeList = secElement.getElementsByTagName("title");
				 
				 if(titleNodeList.getLength() >=1)
				 {
					 title = titleNodeList.item(0).getTextContent();
					 section.setTitle(title);
				 }else
				 {
					 section.setTitle("other");
				 }
				 
				 NodeList paragraphNodeList = secElement.getElementsByTagName("p");
				 
				 if(paragraphNodeList.getLength() >=1)
				 {
					 for (int pIndex = 0; pIndex < paragraphNodeList.getLength(); pIndex++) {
					 
						 String paragraph = paragraphNodeList.item(pIndex).getTextContent();
						 //paragraphList.add(paragraph);
						 paragraphBuilder.append(paragraph);
					 }
					 //section.setParagraphList(paragraphList);
					 section.setParagraphs(paragraphBuilder.toString());
				 }else
				 {
					 //paragraphList.add("");
					 //section.setParagraphList(paragraphList);
					 paragraphBuilder.append("");
					 section.setParagraphs(paragraphBuilder.toString());
				 }
				 
				 sectionList.add(section);
				 

			}
			
		}
		
		abstractText.setSectionList(sectionList);
		articleMeta.setAbstractText(abstractText);
		
		PublicationDate publicationDate = new PublicationDate();
		NodeList publicationDateDayNodeList = (NodeList) xpath.evaluate(
				"//front/article-meta/history/date[@date-type='accepted']/day", nxml, XPathConstants.NODESET);
		
		if(publicationDateDayNodeList.getLength() >=1)
		{
			String date = publicationDateDayNodeList.item(0).getTextContent();
			publicationDate.setDate(date);
		}
		
		NodeList publicationDateMonthNodeList = (NodeList) xpath.evaluate(
				"//front/article-meta/history/date[@date-type='accepted']/month", nxml, XPathConstants.NODESET);
		
		if(publicationDateMonthNodeList.getLength() >=1)
		{
			String month = publicationDateMonthNodeList.item(0).getTextContent();
			publicationDate.setMonth(month);
		}
		
		NodeList publicationDateYearNodeList = (NodeList) xpath.evaluate(
				"//front/article-meta/history/date[@date-type='accepted']/year", nxml, XPathConstants.NODESET);
		
		if(publicationDateYearNodeList.getLength() >=1)
		{
			String year = publicationDateYearNodeList.item(0).getTextContent();
			publicationDate.setYear(year);
		}
		
		articleMeta.setPublicationDate(publicationDate);
		
		return articleMeta;
	}

	private ArticleContent findArticleContent() throws XPathExpressionException{
		
		ArticleContent articleContent = new ArticleContent();
		List<Section> listSection = new ArrayList<Section>();
		
		//List<String> pList = new ArrayList<String>();
		StringBuilder paragrapsBilder = new StringBuilder();
		List<String> titleList = new ArrayList<String>();
		//Section section = new Section();
		
		
		NodeList pNodeList = (NodeList) xpath.evaluate(
				"//body/p", nxml, XPathConstants.NODESET);
				
		// paragraph without title is named other
		if (pNodeList.getLength() >= 1) {
			Section section = new Section();
			//pList = new ArrayList<String>();
			section.setTitle("other");
			for (int i = 0; i < pNodeList.getLength(); i++) {
				String p = pNodeList.item(i).getTextContent();
				//pList.add(p);
				paragrapsBilder.append(p);
			}
			//section.setParagraphList(pList);
			section.setParagraphs(paragrapsBilder.toString());
			listSection.add(section);
			
		}
		
		NodeList secNodeList = (NodeList) xpath.evaluate(
				"//body/sec", nxml, XPathConstants.NODESET);
		// paragraph in section with title
		if (secNodeList.getLength() >= 1) {
			
			
			for (int i = 0; i < secNodeList.getLength(); i++) {
				Section section = new Section();
				Element secElement =(Element) secNodeList.item(i);
				paragrapsBilder = new StringBuilder();
				NodeList titleElement = secElement.getElementsByTagName("title");
				if (titleElement.getLength() >= 1) {
					String title = secElement.getElementsByTagName("title").item(0).getTextContent();
					section.setTitle(title);
					
				}else{
					section.setTitle("other");
				}
				
				NodeList pElements = secElement.getElementsByTagName("p");
				
				if (pElements.getLength() >= 1){
					for (int j = 0; j < pElements.getLength(); j++) {
						String p = pElements.item(j).getTextContent();
						//pList.add(p);
						paragrapsBilder.append(p);
						
					}
					//section.setParagraphList(pList);
					section.setParagraphs(paragrapsBilder.toString());
				}
				listSection.add(section);
				
			}
			
			
			
		}
		articleContent.setSectionList(listSection);
		return articleContent;
		
	}
	
	public ArticleContent parseBodyData() throws XPathExpressionException {

		ArticleContent articleContent= null;
		if (isRead) {

			articleContent = findArticleContent();
			

			return articleContent;

		} else {
			System.out.println("Nxml must read.");
		}

		return articleContent;

	}

}
