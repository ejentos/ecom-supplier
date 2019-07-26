package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.IBrandDAO;
import com.tsa.supplier.service.entity.Brand;
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
public class BrandDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements IBrandDAO {

	@Override
	public Brand get(long id) {
		return this.getJdbcTemplate().query("SELECT * FROM brand WHERE id=?", 
				new BrandExtractor(), id);
	}

	@Override
	public List<Brand> getAll() {
		return this.getJdbcTemplate().query("SELECT * FROM brand ORDER BY name asc", new BrandRowMapper());
	}

	private class BrandExtractor implements ResultSetExtractor<Brand> {

		@Override
		public Brand extractData(ResultSet rs) throws SQLException, DataAccessException {
			Brand brand = null;
			if(rs.next()) {
				brand = new Brand();
				brand.setId(rs.getLong("id"));
				brand.setName(rs.getString("name"));
			}
			return brand;
		}

	}

	private static final class BrandRowMapper implements RowMapper<Brand> {

		public Brand mapRow(ResultSet rs, int rowNum) throws SQLException {
			Brand brand = new Brand();
			brand.setId(rs.getLong("id"));
			brand.setName(rs.getString("name"));
			return brand;
		}

	}

	@Override
	public Brand insert(Brand object) {
		String INSERT_SQL = "INSERT INTO brand (id, name) VALUES (?, ?)";
		this.getJdbcTemplate().update(
				connection -> {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, object.getId());
					ps.setString(2, object.getName());
					return ps;
				});
		return object;
	}

	@Override
	public void update(Brand object) {
		String UPDATE_SQL = "UPDATE brand SET" +
				" name = ?" +
				" WHERE id = ?";
		this.getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
			ps.setString(1, object.getName());
			ps.setLong(2, object.getId());
			return ps;
		});
	}

	@Override
	public Brand merge(Brand object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		this.getJdbcTemplate().update("DELETE FROM brand WHERE id= ?", id);
	}

	@Override
	public String getBrandNameByGoodId(long goodId) {
		return this.getJdbcTemplate().queryForObject("SELECT name FROM brand WHERE id=(SELECT brand_id FROM good WHERE id=?)", new Object[] { goodId } ,String.class);
	}

}