/**
 * 
 */
package com.nxmlindex.service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import com.nxmlindex.common.vo.ArticleMeta;
import com.nxmlindex.util.NxmlParser;

/**
 * <pre>
 * com.nxmlindex.service
 *   |_ NxmlParserService.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 5. 12. 오후 5:54:43
 * @Version : 
 */
public class NxmlParserService {

	
	private NxmlParser nxmlParser;

	
	public void initNxmlParser() {

		try {
			nxmlParser = new NxmlParser();

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArticleMeta parseFrontInNxml(String path) {

		nxmlParser.readNxml(path);
		ArticleMeta articleMeta=null;
		try {
			articleMeta = nxmlParser.parseFrontData();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return articleMeta;

	}

}
