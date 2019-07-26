package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.IFilePriceDAO;
import com.tsa.supplier.service.entity.FilePrice;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;
import java.util.List;

@Repository
public class FilePriceDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements IFilePriceDAO {

	@Override
	public FilePrice get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilePrice insert(FilePrice object) {
		String INSERT_SQL = "INSERT INTO job_file_price (file_name, date_file_received, provider_id) VALUES (?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(
				connection -> {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, object.getFileName());
					ps.setTimestamp(2, new Timestamp(object.getReceived().getTime()));
					ps.setLong(3, object.getProviderId());
					return ps;
				}, keyHolder);
		object.setId(keyHolder.getKey().longValue());
		return object;
	}

	@Override
	public void update(FilePrice object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FilePrice merge(FilePrice object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<FilePrice> getAll() {
		return this.getJdbcTemplate().query("SELECT * FROM job_file_price", new FilePriceMapper());
	}

	@Override
	public List<FilePrice> getAll(boolean processed) {
		List<FilePrice> list = null;
		if(processed) {
			list = this.getJdbcTemplate().query("SELECT * FROM job_file_price where processed=1 order by date_file_received", new FilePriceMapper());
		} else {
			list = this.getJdbcTemplate().query("SELECT * FROM job_file_price where processed=0 order by date_file_received", new FilePriceMapper());
		}
		return list;
	}

	private static final class FilePriceMapper implements RowMapper<FilePrice> {

		public FilePrice mapRow(ResultSet rs, int rowNum) throws SQLException {
			FilePrice filePrice = new FilePrice();
			filePrice.setId(rs.getLong("id"));
			filePrice.setFileName(rs.getString("file_name"));
			filePrice.setProcessed(rs.getBoolean("processed"));
			filePrice.setProviderId(rs.getLong("provider_id"));
			return filePrice;
		}

	}

	@Override
	public Date getLastDownloadedDate() {
		Date lastDownloaded = this.getJdbcTemplate().queryForObject(
				"SELECT max(date_file_received) \n" +
				"FROM job_file_price",Date.class);
		return lastDownloaded;
	}

	@Override
	public void setProcessed(long id) {
		this.getJdbcTemplate().update("UPDATE job_file_price SET processed=1 where id=?", id);
	}

}