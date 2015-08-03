/**
 * 
 */
package com.nxmlindex.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nxmlindex.common.vo.Article;
import com.nxmlindex.common.vo.ArticleBody;
import com.nxmlindex.common.vo.ArticleBodyExist;

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
public interface ArticleBodyExistRepository extends MongoRepository<ArticleBodyExist, String>{

}
