package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.IGoodDAO;
import com.tsa.supplier.service.entity.Good;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class GoodDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements IGoodDAO {

	@Override
	public Good get(long id) {
		return this.getJdbcTemplate().query("SELECT * from good where id=?", new GoodExtractor(), id);
	}

	@Override
	public Good insert(Good object) {
		String INSERT_SQL = "INSERT INTO good (id, category_id,brand_id,name,price_raw,price) VALUES (?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(
				connection -> {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, object.getId());
					ps.setLong(2, object.getCategoryId());
					ps.setLong(3, object.getBrandId());
					ps.setString(4, object.getName());
					ps.setBigDecimal(5, object.getPriceRaw());
					ps.setBigDecimal(6, object.getPrice());
					return ps;
				}, keyHolder);
		object.setId(keyHolder.getKey().longValue());
		return object;
	}

	@Override
	public void update(Good object) {
		String UPDATE_SQL = "UPDATE good" + 
				" SET" + 
				" category_id = IFNULL(NULLIF(?,0),category_id)," + 
				" brand_id = IFNULL(NULLIF(?,0),brand_id)," + 
				" name = IFNULL(NULLIF(?,''),name)," + 
				" WHERE id = ?";
		this.getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
				ps.setLong(1, object.getCategoryId());
				ps.setLong(2, object.getBrandId());
				ps.setString(3, object.getName());
				ps.setLong(4, object.getId());
			return ps;
		});
	}

	@Override
	public Good merge(Good object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		this.getJdbcTemplate().update("DELETE FROM good WHERE id= ?", id);
	}

	@Override
	public List<Good> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private static final class GoodRowMapper implements RowMapper<Good> {

		public Good mapRow(ResultSet rs, int rowNum) throws SQLException {
			return extractGood(rs);
		}

	}

	public static class GoodExtractor implements ResultSetExtractor<Good> {
		
		@Override
		public Good extractData(ResultSet rs) throws SQLException,
                DataAccessException {
			Good good = null;
			if(rs.next()) {
				good = extractGood(rs);
			}
			return good;
		}
		
	}

	private static Good extractGood(ResultSet rs) throws SQLException {
		Good good = null;
		if(rs!= null) {
			good = new Good();
			good.setId(rs.getLong("id"));
			good.setBrandId(rs.getLong("brand_id"));
			good.setCategoryId(rs.getLong("category_id"));
			good.setName(rs.getString("name"));
			good.setPriceRaw(rs.getBigDecimal("price_raw"));
			good.setPrice(rs.getBigDecimal("price"));
		}
		return good;
	}

}