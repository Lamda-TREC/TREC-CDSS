/**
 * 
 */
package com.nxmlindex.util;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.nxmlindex.common.vo.Article;

/**
 * <pre>
 * com.nxmlindex.util
 *   |_ JsonConverter.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 5. 12. 오후 5:49:18
 * @Version : 
 */
public class JsonConverter {
	
	
	public boolean ObjectToJson(String pmcid, Article article){
		
		ObjectMapper mapper = new ObjectMapper();
		boolean result =false;
		try {
			mapper.writeValue(new File("D:\\문수\\DATA\\TRECDATA\\Json\\"+pmcid+".json"), article);
			result = true;	
			
		} catch (JsonGenerationException e) {
			result = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			result = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			result = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return result;
		}
		
	}

}
