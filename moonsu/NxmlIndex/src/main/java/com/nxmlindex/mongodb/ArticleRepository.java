package com.nxmlindex.mongodb;







import org.springframework.data.mongodb.repository.MongoRepository;

import com.nxmlindex.common.vo.Article;

public interface ArticleRepository extends MongoRepository<Article, String>{
	
	public Article findByArticleMetaPmcid(String pmcid);
	
}
