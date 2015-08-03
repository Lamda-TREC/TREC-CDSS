/**
 * 
 */
package com.nxmlindex.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nxmlindex.common.vo.ArticleAnswerData;

/**
 * <pre>
 * com.nxmlindex.mongodb
 *   |_ ArticleAnswerDataRepository.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 7. 16. 오전 1:48:36
 * @Version : 
 */
public interface ArticleAnswerDataRepository extends MongoRepository<ArticleAnswerData, String>{

}
