package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.IProviderDAO;
import com.tsa.supplier.service.entity.Provider;
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
public class ProviderDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements IProviderDAO {

	@Override
	public Provider get(long id) {
		return this.getJdbcTemplate().query("SELECT * FROM provider WHERE id=?", 
				new ProviderExtractor(), id);
	}

	@Override
	public Provider insert(Provider object) {
		String INSERT_SQL = "INSERT INTO provider (name,price_email,active) VALUES (?,?,?);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(
				connection -> {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, object.getName());
					ps.setString(2, object.getPriceEmail());
					ps.setBoolean(3, object.isActive());
					return ps;
				}, keyHolder);
		object.setId(keyHolder.getKey().longValue());
		return object;
	}

	@Override
	public void update(Provider object) {
		String UPDATE_SQL = "UPDATE provider SET" +
				" name = ?" +
				",price_email = ?" +
				",active = ?" +
				" WHERE id = ?";
		this.getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
			ps.setString(1, object.getName());
			ps.setString(2, object.getPriceEmail());
			ps.setBoolean(3, object.isActive());
			ps.setLong(4, object.getId());
			return ps;
		});
	}

	@Override
	public Provider merge(Provider object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		this.getJdbcTemplate().update("DELETE FROM provider WHERE id= ?", id);
	}

	@Override
	public List<Provider> getAll() {
		return this.getJdbcTemplate().query("SELECT * FROM provider", new ProviderMapper());
	}

	private static final class ProviderMapper implements RowMapper<Provider> {

		public Provider mapRow(ResultSet rs, int rowNum) throws SQLException {
			Provider provider = new Provider();
			provider.setId(rs.getLong("id"));
			provider.setName(rs.getString("name"));
			provider.setPriceEmail(rs.getString("price_email"));
			provider.setActive(rs.getBoolean("active"));
			return provider;
		}

	}
	
	private class ProviderExtractor implements ResultSetExtractor<Provider> {

		@Override
		public Provider extractData(ResultSet rs) throws SQLException, DataAccessException {
			Provider provider = null;
			if(rs.next()) {
				provider = new Provider();
				provider.setId(rs.getLong("id"));
				provider.setName(rs.getString("name"));
				provider.setPriceEmail(rs.getString("price_email"));
				provider.setActive(rs.getBoolean("active"));
			}
			return provider;
		}

	}

}