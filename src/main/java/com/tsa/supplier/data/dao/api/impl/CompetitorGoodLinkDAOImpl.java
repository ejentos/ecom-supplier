package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.ICompetitorGoodLinkDAO;
import com.tsa.supplier.service.entity.CompetitorGoodLink;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class CompetitorGoodLinkDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements ICompetitorGoodLinkDAO {

	@Override
	public CompetitorGoodLink get(long id) {
		return this.getJdbcTemplate().query("SELECT * FROM competitor_good_link WHERE id=?", new CompetitorGoodLinkExtractor(), id);
	}

	@Override
	public CompetitorGoodLink insert(CompetitorGoodLink object) {
		String INSERT_SQL = "INSERT INTO competitor_good_link (good_id,competitor_id,url,active,low_price,high_price) VALUES (?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
						ps.setLong(1, object.getGoodId());
						ps.setLong(2, object.getCompetitorId());
						ps.setString(3, object.getUrl());
						ps.setBoolean(4, object.isActive());
						ps.setBigDecimal(5, object.getLowPrice());
						ps.setBigDecimal(6, object.getHighPrice());
						return ps;
					}
				}, keyHolder);
		object.setId(keyHolder.getKey().longValue());
		return object;
	}

	@Override
	public void update(CompetitorGoodLink object) {
		String UPDATE_SQL = "UPDATE competitor_good_link SET" +
				" good_id = ?" +
				",competitor_id = ?" +
				",url = ?" +
				",active = ?" +
				",low_price = ?" +
				",high_price = ?" +
				" WHERE id = ?";
		this.getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
			ps.setLong(1, object.getGoodId());
			ps.setLong(2, object.getCompetitorId());
			ps.setString(3, object.getUrl());
			ps.setBoolean(4, object.isActive());
			ps.setBigDecimal(5, object.getLowPrice());
			ps.setBigDecimal(6, object.getHighPrice());
			ps.setLong(7, object.getId());
			return ps;
		});
	}

	@Override
	public CompetitorGoodLink merge(CompetitorGoodLink object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		this.getJdbcTemplate().update("DELETE FROM competitor_good_link WHERE id= ?", id);
	}

	@Override
	public List<CompetitorGoodLink> getAll() {
		return this.getJdbcTemplate().query("SELECT * FROM competitor_good_link", new CompetitorGoodLinkRowMapper());
	}

	public static class CompetitorGoodLinkExtractor implements ResultSetExtractor<CompetitorGoodLink> {
		
		@Override
		public CompetitorGoodLink extractData(ResultSet rs) throws SQLException,
                DataAccessException {
			CompetitorGoodLink competitorGoodLink = null;
			if(rs.next()) {
				competitorGoodLink = new CompetitorGoodLink();
				competitorGoodLink.setId(rs.getLong("id"));
				competitorGoodLink.setGoodId(rs.getLong("good_id"));
				competitorGoodLink.setCompetitorId(rs.getLong("competitor_id"));
				competitorGoodLink.setUrl(rs.getString("url"));
				competitorGoodLink.setActive(rs.getBoolean("active"));
				competitorGoodLink.setLowPrice(rs.getBigDecimal("low_price"));
				competitorGoodLink.setHighPrice(rs.getBigDecimal("high_price"));
			}
			return competitorGoodLink;
		}
		
	}

	private static final class CompetitorGoodLinkRowMapper implements RowMapper<CompetitorGoodLink> {

		public CompetitorGoodLink mapRow(ResultSet rs, int rowNum) throws SQLException {
			CompetitorGoodLink competitorGoodLink = new CompetitorGoodLink();
			competitorGoodLink.setId(rs.getLong("id"));
			competitorGoodLink.setGoodId(rs.getLong("good_id"));
			competitorGoodLink.setCompetitorId(rs.getLong("competitor_id"));
			competitorGoodLink.setUrl(rs.getString("url"));
			competitorGoodLink.setActive(rs.getBoolean("active"));
			competitorGoodLink.setLowPrice(rs.getBigDecimal("low_price"));
			competitorGoodLink.setHighPrice(rs.getBigDecimal("high_price"));
			return competitorGoodLink;
		}

	}

	@Override
	public List<CompetitorGoodLink> getAllByCompetitorId(long competitorId) {
		return this.getJdbcTemplate().query("SELECT * FROM competitor_good_link WHERE competitor_id=?", new CompetitorGoodLinkRowMapper(), competitorId);
	}

	@Override
	public List<CompetitorGoodLink> getAllByGoodId(long goodId) {
		return this.getJdbcTemplate().query("SELECT * FROM competitor_good_link WHERE good_id=?", new CompetitorGoodLinkRowMapper(), goodId);
	}
	
}
