package com.tsa.supplier.data.dao.api.impl;

import com.tsa.supplier.data.dao.api.IProviderPriceUploadSettingsDAO;
import com.tsa.supplier.service.entity.ProviderPriceUploadSettings;
import com.tsa.supplier.service.enums.OfferDeliveryFileFormat;
import com.tsa.supplier.service.enums.OfferDeliveryFileStorageType;
import com.tsa.supplier.service.enums.OfferDeliveryType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProviderPriceUploadSettingsDAOImpl extends NamedParameterJdbcDaoSupportWrapper implements IProviderPriceUploadSettingsDAO {

	@Override
	public ProviderPriceUploadSettings get(long id) {
		return this.getJdbcTemplate().query(
				"SELECT * FROM provider_price_upload_settings WHERE id = ?",
				new ProviderPriceUploadSettingsExtractor(),
				id);
	}

	@Override
	public ProviderPriceUploadSettings getByProviderId(long providerId) {
		return this.getJdbcTemplate().query(
				"SELECT * FROM provider_price_upload_settings WHERE provider_id = ?",
				new ProviderPriceUploadSettingsExtractor(),
				providerId);
	}

	@Override
	public ProviderPriceUploadSettings insert(ProviderPriceUploadSettings object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ProviderPriceUploadSettings object) {
		// TODO Auto-generated method stub

	}

	@Override
	public ProviderPriceUploadSettings merge(ProviderPriceUploadSettings object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProviderPriceUploadSettings> getAll() {
		return this.getJdbcTemplate().query("SELECT * FROM provider_price_upload_settings", new ProviderFileUploadSettingsRowMapper());
	}

	public static class ProviderPriceUploadSettingsExtractor implements ResultSetExtractor<ProviderPriceUploadSettings> {

		@Override
		public ProviderPriceUploadSettings extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			ProviderPriceUploadSettings settings = null;
			if(rs.next()) {
				settings = extractProviderPriceUploadSettings(rs);
			}
			return settings;
		}

	}

	private static final class ProviderFileUploadSettingsRowMapper implements RowMapper<ProviderPriceUploadSettings> {

		public ProviderPriceUploadSettings mapRow(ResultSet rs, int rowNum) throws SQLException {
			return extractProviderPriceUploadSettings(rs);
		}

	}

	private static ProviderPriceUploadSettings extractProviderPriceUploadSettings(ResultSet rs) throws SQLException {
		ProviderPriceUploadSettings object = null;
		if(rs != null) {
			object = new ProviderPriceUploadSettings();
			object.setId(rs.getLong("id"));
			object.setProviderId(rs.getLong("provider_id"));
			object.setArticlePosition(rs.getInt("article_position"));
			object.setBrandPosition(rs.getInt("brand_position"));
			object.setPricePosition(rs.getInt("price_position"));
			object.setCountPosition(rs.getInt("count_position"));
			object.setMultiplicityPosition(rs.getInt("multiplicity_position"));
			object.setCurrencyPosition(rs.getInt("currency_position"));
			object.setHeader(rs.getBoolean("header"));
			object.setOfferDeliveryType(OfferDeliveryType.getOfferDeliveryType(rs.getByte("offerDeliveryType")));
			object.setOfferDeliveryFileStorageType(OfferDeliveryFileStorageType.getOfferDeliveryFileStorageType(rs.getByte("offerDeliveryFileStorageType")));
			object.setOfferDeliveryFileFormat(OfferDeliveryFileFormat.getOfferDeliveryFileFormat(rs.getByte("offerDeliveryFileFormat")));
			object.setSeparator(rs.getString("separator").charAt(0));
			object.setDeliveryPosition(rs.getInt("delivery_position"));
			object.setQuote(rs.getBoolean("quote"));
			object.setJobClassName(rs.getString("jobClassName"));
			object.setJobCronTime(rs.getString("cron_time"));
			object.setProviderEmail(rs.getString("price_email"));
		}
		return object;
	}

	@Override
	public List<ProviderPriceUploadSettings> getSchedulingProviderSettings() {
		return this.getJdbcTemplate().query("SELECT * FROM provider_price_upload_settings ppus" +
				" join provider p on p.id=ppus.provider_id" +
				" where p.active=1" +
				" and ppus.cron_time is not null", new ProviderFileUploadSettingsRowMapper());
	}

}