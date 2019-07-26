package com.tsa.supplier.service.impl;

import com.tsa.supplier.data.dao.api.IArticleDAO;
import com.tsa.supplier.service.entity.Article;
import com.tsa.supplier.service.IArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceAbstract<Article, IArticleDAO> implements IArticleService {

	@Override
	public void delete(long id) {
		// TODO cascade remove from provider offer table (db level)
		dao.delete(id);
	}

	@Override
	public void delete(Article article) {
		this.delete(article.getId());
	}

	@Override
	public List<Article> getArticlesByGoodId(long goodId) {
		return dao.getArticlesByGoodId(goodId);
	}

	@Override
	public void deleteAndUpdateGoodData(long id, long goodId, long categoryId) {
		// TODO cascade remove from provider offer table (db level)
		dao.delete(id);
	}

}