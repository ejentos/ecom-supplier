package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.ICompetitorDAO;
import com.tsa.supplier.service.entity.Competitor;
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
public class CompetitorDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements ICompetitorDAO {

	@Override
	public Competitor get(long id) {
		return this.getJdbcTemplate().query("SELECT * FROM competitor WHERE id=?", new CompetitorExtractor(), id);
	}

	@Override
	public Competitor insert(Competitor object) {
		String INSERT_SQL = "INSERT INTO competitor (name, active) VALUES (?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(
				connection -> {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, object.getName());
					ps.setBoolean(2, object.isActive());
					return ps;
				}, keyHolder);
		object.setId(keyHolder.getKey().longValue());
		return object;
	}

	@Override
	public void update(Competitor object) {
		String UPDATE_SQL = "UPDATE competitor SET" +
				" name = ?" +
				",active = ?" +
				" WHERE id = ?";
		this.getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
			ps.setString(1, object.getName());
			ps.setBoolean(2, object.isActive());
			ps.setLong(3, object.getId());
			return ps;
		});
	}

	@Override
	public Competitor merge(Competitor object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		this.getJdbcTemplate().update("DELETE FROM competitor WHERE id= ?", id);
	}

	@Override
	public List<Competitor> getAll() {
		return this.getJdbcTemplate().query("SELECT * FROM competitor", new CompetitorRowMapper());
	}
	
	public static class CompetitorExtractor implements ResultSetExtractor<Competitor> {
		
		@Override
		public Competitor extractData(ResultSet rs) throws SQLException,
                DataAccessException {
			Competitor competitor = null;
			if(rs.next()) {
				competitor = new Competitor();
				competitor.setId(rs.getLong("id"));
				competitor.setName(rs.getString("name"));
				competitor.setActive(rs.getBoolean("active"));
			}
			return competitor;
		}
		
	}

	private static final class CompetitorRowMapper implements RowMapper<Competitor> {

		public Competitor mapRow(ResultSet rs, int rowNum) throws SQLException {
			Competitor competitor = new Competitor();
			competitor.setId(rs.getLong("id"));
			competitor.setName(rs.getString("name"));
			competitor.setActive(rs.getBoolean("active"));
			return competitor;
		}

	}

}