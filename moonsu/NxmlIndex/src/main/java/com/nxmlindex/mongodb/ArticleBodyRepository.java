/**
 * 
 */
package com.nxmlindex.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nxmlindex.common.vo.Article;
import com.nxmlindex.common.vo.ArticleAbstract;
import com.nxmlindex.common.vo.ArticleBody;

/**
 * <pre>
 * com.nxmlindex.mongodb
 *   |_ ArticleFullRepository.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 6. 10. 오후 10:50:03
 * @Version : 
 */
public interface ArticleBodyRepository extends MongoRepository<ArticleBody, String>{

	
	public ArticleBody findByPmcid(String pmcid);

	/*@Query("{pmcid : ?0}")
	public ArticleBody findByPmcidQuery(String pmcid);*/
	
	
}
