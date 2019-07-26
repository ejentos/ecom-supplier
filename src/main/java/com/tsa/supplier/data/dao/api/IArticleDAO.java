package com.tsa.supplier.data.dao.api;

import com.tsa.supplier.service.entity.Article;

import java.util.List;

public interface IArticleDAO extends CRUD<Article> {

	List<Article> getArticlesByGoodId(long goodId);

	void deleteByGoodId(long goodId);

}