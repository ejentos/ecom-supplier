package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.IArticleDAO;
import com.tsa.supplier.service.entity.Article;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ArticleDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements IArticleDAO {

	@Override
	public Article get(long id) {
		return this.getJdbcTemplate().query("SELECT * FROM article WHERE id=?", new ArticleExtractor(), id);
	}

	@Override
	public List<Article> getArticlesByGoodId(long goodId) {
		return this.getJdbcTemplate().query("SELECT * FROM article WHERE good_id=?", new ArticleRowMapper(), goodId);
	}

	@Override
	public Article insert(Article object) {
		String INSERT_SQL = "INSERT INTO article (good_id, article, mnf_country, brand_id) VALUES (?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(
				connection -> {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, object.getGoodId());
					ps.setString(2, object.getArticle());
					ps.setString(3, object.getManufactureCountry());
					ps.setLong(4, object.getBrandId());
					return ps;
				}, keyHolder);
		object.setId(keyHolder.getKey().longValue());
		return object;
	}

	@Override
	public void update(Article object) {
		String UPDATE_SQL = "UPDATE article SET" +
				" good_id = ?" +
				",article = ?" +
				",mnf_country = ?" +
				",brand_name = ?" +
				" WHERE id = ?";
		this.getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
			ps.setLong(1, object.getGoodId());
			ps.setString(2, object.getArticle());
			ps.setString(3, object.getManufactureCountry());
			ps.setLong(4, object.getBrandId());
			ps.setLong(5, object.getId());
			return ps;
		});
	}

	@Override
	public Article merge(Article object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		this.getJdbcTemplate().update("DELETE FROM article WHERE id= ?", id);
	}

	@Override
	public List<Article> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public static final class ArticleExtractor implements ResultSetExtractor<Article> {

		@Override
		public Article extractData(ResultSet rs) throws SQLException,
                DataAccessException {
			Article article = null;
			if(rs.next()) {
				article = new Article();
				article.setId(rs.getLong("id"));
				article.setGoodId(rs.getLong("good_id"));
				article.setArticle(rs.getString("article"));
				article.setManufactureCountry(rs.getString("mnf_country"));
				article.setBrandId(rs.getLong("brand_id"));
			}
			return article;
		}

	}

	private static final class ArticleRowMapper implements RowMapper<Article> {

		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Article article = new Article();
			article.setId(rs.getLong("id"));
			article.setGoodId(rs.getLong("good_id"));
			article.setArticle(rs.getString("article"));
			article.setManufactureCountry(rs.getString("mnf_country"));
			article.setBrandId(rs.getLong("brand_id"));
			return article;
		}

	}

	@Override
	public void deleteByGoodId(long goodId) {
		this.getJdbcTemplate().update("DELETE FROM article WHERE good_id= ?", goodId);
	}

}