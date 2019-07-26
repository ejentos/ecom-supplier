package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.ICategoryDAO;
import com.tsa.supplier.service.entity.Category;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class CategoryDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements ICategoryDAO {

	@Override
	public Category get(long id) {
		return this.getJdbcTemplate().query(
				"SELECT * FROM category WHERE id = ?",
				new CategoryExtractor(),
				id);
	}

	@Override
	public Category insert(Category category) {
		String INSERT_SQL = "INSERT INTO category (id, name) VALUES (?,?)";
		this.getJdbcTemplate().update(
				connection -> {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, category.getId());
					ps.setString(2, category.getName());
					return ps;
				});
		return category;
	}

	@Override
	public void update(Category object) {
		String UPDATE_SQL = "UPDATE category SET" +
				" name = ?" +
				" WHERE id = ?";
		this.getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
			ps.setLong(1, object.getId());
			ps.setString(2, object.getName());
			return ps;
		});
	}

	@Override
	public Category merge(Category object) {
		return null;
	}

	@Override
	public void delete(long id) {
		this.getJdbcTemplate().update("DELETE FROM category WHERE id= ?", id);
	}

	@Override
	public List<Category> getAll() {
		return this.getJdbcTemplate().query("SELECT * FROM category order by parent_id", new CategoryRowMapper());
	}

	private static final class CategoryRowMapper implements RowMapper<Category> {

		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			Category category = new Category();
			category.setId(rs.getLong("id"));
			category.setName(rs.getString("name"));
			return category;
		}

	}

	public static class CategoryExtractor implements ResultSetExtractor<Category> {
		
		@Override
		public Category extractData(ResultSet rs) throws SQLException,
                DataAccessException {
			Category category = null;
			if(rs.next()) {
				category = new Category();
				category.setId(rs.getLong("id"));
				category.setName(rs.getString("name"));
			}
			return category;
		}

	}

}