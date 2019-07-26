package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.IProviderOfferDAO;
import com.tsa.supplier.service.entity.ProviderOffer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ProviderOfferDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements IProviderOfferDAO {

	@Override
	public ProviderOffer get(long id) {
		return this.getJdbcTemplate().query("SELECT * FROM provider_offer WHERE id=?", new ProviderOfferExtractor(), id);
	}

	@Override
	public ProviderOffer insert(ProviderOffer object) {
		String INSERT_SQL = "INSERT INTO provider_offer(provider_id,article_id,price,count,multiplicity,delivery) VALUES (?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(
				connection -> {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, object.getProviderId());
					ps.setLong(2, object.getArticleId());
					ps.setBigDecimal(3, object.getPrice());
					ps.setInt(4, object.getCount());
					ps.setString(5, object.getMultiplicity());
					if(object.getDelivery() != null) {
						ps.setTimestamp(6, new Timestamp(object.getDelivery().getTime()));
					} else {
						ps.setTimestamp(6, null);
					}
					return ps;
				}, keyHolder);
		object.setId(keyHolder.getKey().longValue());
		return object;
	}

	@Override
	public void update(ProviderOffer object) {
		String UPDATE_SQL = "UPDATE provider_offer SET" +
				" provider_id = ?" +
				",article_id = ?" +
				",price = ?" +
				",count = ?" +
				",multiplicity = ?" + 
				",delivery = ?" +
				",is_price_actual = ?" +
				" WHERE id = ?";
		this.getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
			ps.setLong(1, object.getProviderId());
			ps.setLong(2, object.getArticleId());
			ps.setBigDecimal(3, object.getPrice());
			ps.setInt(4, object.getCount());
			ps.setString(5, object.getMultiplicity());
			if(object.getDelivery() != null) {
				ps.setTimestamp(6, new Timestamp(object.getDelivery().getTime()));
			} else {
				ps.setTimestamp(6, null);
			}
			ps.setLong(7, object.getId());
			ps.setBoolean(8, object.isActualPrice());
			return ps;
		});
	}

	@Override
	public ProviderOffer merge(ProviderOffer object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		this.getJdbcTemplate().update("DELETE FROM provider_offer WHERE id= ?", id);
	}

	@Override
	public List<ProviderOffer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] insertBatch(List<ProviderOffer> offers) {
		 int[] updateCounts = this.getJdbcTemplate().batchUpdate(
				"INSERT INTO provider_offer(provider_id,article_id,price,count,multiplicity,delivery) VALUES (?,?,?,?,?,?)",
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ProviderOffer offer = offers.get(i);
							ps.setLong(1, offer.getProviderId());
							ps.setLong(2, offer.getArticleId());
							ps.setBigDecimal(3, offer.getPrice());
							ps.setInt(4, offer.getCount());
							ps.setString(5, offer.getMultiplicity());
							if(offer.getDelivery() != null) {
								ps.setTimestamp(6, new Timestamp(offer.getDelivery().getTime()));
							} else {
								ps.setTimestamp(6, null);
							}
						}
						
						public int getBatchSize() {
							return offers.size();
						}
				 });
		return updateCounts;
	}

	@Override
	public int[] updateBatch(List<ProviderOffer> offers) {
		 int[] updateCounts = this.getJdbcTemplate().batchUpdate(
					"UPDATE provider_offer SET price = ?, count = ?, multiplicity = ?, delivery = ?, is_price_actual = 1 WHERE id = ?",
						new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ProviderOffer offer = offers.get(i);
								ps.setBigDecimal(1, offer.getPrice());
								ps.setInt(2, offer.getCount());
								ps.setString(3, offer.getMultiplicity());
								if(offer.getDelivery() != null) {
									ps.setTimestamp(4, new Timestamp(offer.getDelivery().getTime()));
								} else {
									ps.setTimestamp(4, null);
								}
								ps.setLong(5, offer.getId());
							}
							
							public int getBatchSize() {
								return offers.size();
							}
					 });
			return updateCounts;
	}
	
	private static final class ProviderOfferRowMapper implements RowMapper<ProviderOffer> {

		public ProviderOffer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return extractProviderOffer(rs);
		}

	}

	public static class ProviderOfferExtractor implements ResultSetExtractor<ProviderOffer> {
		
		@Override
		public ProviderOffer extractData(ResultSet rs) throws SQLException,
                DataAccessException {
			ProviderOffer providerOffer = null;
			if(rs.next()) {
				providerOffer = extractProviderOffer(rs);
			}
			return providerOffer;
		}
		
	}

	private static ProviderOffer extractProviderOffer(ResultSet rs) throws SQLException {
		ProviderOffer providerOffer = null;
		if(rs != null) {
			providerOffer = new ProviderOffer();
			providerOffer.setId(rs.getLong("id"));
			providerOffer.setProviderId(rs.getLong("provider_id"));
			providerOffer.setArticleId(rs.getLong("article_id"));
			providerOffer.setPrice(rs.getBigDecimal("price"));
			providerOffer.setCount(rs.getInt("count"));
			providerOffer.setMultiplicity(rs.getString("multiplicity"));
			providerOffer.setDelivery(rs.getDate("delivery"));
			providerOffer.setActualPrice(rs.getBoolean("is_price_actual"));
		}
		return providerOffer;
	}
	
	@Override
	public List<ProviderOffer> getProviderOffersByGoodId(long goodId) {
		return this.getJdbcTemplate().query("SELECT * FROM provider_offer WHERE good_id=?", new ProviderOfferRowMapper(), goodId);
	}

	@Override
	public List<ProviderOffer> getProviderOffersByArticleId(long articleId) {
		return this.getJdbcTemplate().query("SELECT * FROM provider_offer WHERE article_id=?", new ProviderOfferRowMapper(), articleId);
	}

	@Override
	public void deleteAllByProviderId(long providerId) {
		this.getJdbcTemplate().update("DELETE FROM provider_offer WHERE provider_id=?", providerId);
	}

	@Override
	public void deleteByGoodId(long id) {
		this.getJdbcTemplate().update("DELETE FROM provider_offer WHERE article_id IN (SELECT id FROM article WHERE good_id = ?)", id);
	}

	@Override
	public void setStalePriceFlag(long providerId) {
		this.getJdbcTemplate().update("UPDATE provider_offer SET is_price_actual = 0 WHERE provider_id = ?", providerId);
	}
}