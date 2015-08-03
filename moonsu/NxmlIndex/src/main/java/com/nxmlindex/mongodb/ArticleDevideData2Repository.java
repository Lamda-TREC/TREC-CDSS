/**
 * 
 */
package com.nxmlindex.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nxmlindex.common.vo.ArticleDevideData2;

/**
 * <pre>
 * com.nxmlindex.mongodb
 *   |_ ArticleSmallDataRepository.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 8. 오후 9:28:44
 * @Version : 
 */
public interface ArticleDevideData2Repository extends MongoRepository<ArticleDevideData2, String>{
	

	public ArticleDevideData2 findByPmcid(String pmcid); 

}
