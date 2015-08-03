/**
 * 
 */
package com.nxmlindex.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nxmlindex.common.vo.Article;
import com.nxmlindex.common.vo.ArticleAbstract;

/**
 * <pre>
 * com.nxmlindex.mongodb
 *   |_ ArticleAbstractRepository.java
 * </pre>
 * 
 * Desc :
 * 
 * @Company : LAMDA in Ajou Univ
 * @Author : ChaMunsu
 * @Date : 2015. 6. 10. 오후 10:49:52
 * @Version :
 */
public interface ArticleAbstractRepository extends
		MongoRepository<ArticleAbstract, String> {

	
	public ArticleAbstract findByPmcid(String pmcid);

	
	
/*	@Query("{pmcid : ?0}")
	public ArticleAbstract findByPmcidQuery(String pmcid);*/

}
