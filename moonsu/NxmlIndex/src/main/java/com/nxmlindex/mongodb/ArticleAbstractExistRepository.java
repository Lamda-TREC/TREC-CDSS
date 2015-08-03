/**
 * 
 */
package com.nxmlindex.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nxmlindex.common.vo.Article;
import com.nxmlindex.common.vo.ArticleAbstract;
import com.nxmlindex.common.vo.ArticleAbstractExist;

/**
 * <pre>
 * com.nxmlindex.mongodb
 *   |_ ArticleAbstractRepository.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 6. 10. 오후 10:49:52
 * @Version : 
 */
public interface ArticleAbstractExistRepository extends MongoRepository<ArticleAbstractExist, String>{

}
