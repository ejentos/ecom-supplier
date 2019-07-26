package com.tsa.supplier.service;

import com.tsa.supplier.service.entity.Article;

import java.util.List;

public interface IArticleService extends CRUD<Article> {

	void deleteAndUpdateGoodData(long id, long goodId, long categoryId);

	List<Article> getArticlesByGoodId(long goodId);

	void delete(long id);

}