/**
 * 
 */
package com.nxmlindex.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nxmlindex.common.vo.ArticleDevideData3;

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
public interface ArticleDevideData3Repository extends MongoRepository<ArticleDevideData3, String>{
	

	public ArticleDevideData3 findByPmcid(String pmcid); 

}
