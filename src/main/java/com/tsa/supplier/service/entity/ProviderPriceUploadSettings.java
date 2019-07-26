package com.tsa.supplier.service.entity;

import com.tsa.supplier.service.enums.OfferDeliveryFileFormat;
import com.tsa.supplier.service.enums.OfferDeliveryFileStorageType;
import com.tsa.supplier.service.enums.OfferDeliveryType;

public class ProviderPriceUploadSettings extends Base {

	private long providerId;
	private String providerEmail;

	private boolean header;
	private boolean quote;
	private char separator;

	private int articlePosition;
	private int brandPosition;
	private int pricePosition;
	private int countPosition;
	private int multiplicityPosition;
	private int currencyPosition;
	private int deliveryPosition;

	private OfferDeliveryType offerDeliveryType;
	private OfferDeliveryFileFormat offerDeliveryFileFormat;
	private OfferDeliveryFileStorageType offerDeliveryFileStorageType;

	private String jobClassName;
	private String jobCronTime;

	public long getProviderId() {
		return providerId;
	}
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}
	public boolean isHeader() {
		return header;
	}
	public void setHeader(boolean header) {
		this.header = header;
	}
	public int getArticlePosition() {
		return articlePosition;
	}
	public void setArticlePosition(int articlePosition) {
		this.articlePosition = articlePosition;
	}
	public int getBrandPosition() {
		return brandPosition;
	}
	public void setBrandPosition(int brandPosition) {
		this.brandPosition = brandPosition;
	}
	public int getPricePosition() {
		return pricePosition;
	}
	public void setPricePosition(int pricePosition) {
		this.pricePosition = pricePosition;
	}
	public int getCountPosition() {
		return countPosition;
	}
	public void setCountPosition(int countPosition) {
		this.countPosition = countPosition;
	}
	public int getMultiplicityPosition() {
		return multiplicityPosition;
	}
	public void setMultiplicityPosition(int multiplicityPosition) {
		this.multiplicityPosition = multiplicityPosition;
	}
	public int getCurrencyPosition() {
		return currencyPosition;
	}
	public void setCurrencyPosition(int currencyPosition) {
		this.currencyPosition = currencyPosition;
	}
	public char getSeparator() {
		return separator;
	}
	public void setSeparator(char separator) {
		this.separator = separator;
	}
	public int getDeliveryPosition() {
		return deliveryPosition;
	}
	public void setDeliveryPosition(int deliveryPosition) {
		this.deliveryPosition = deliveryPosition;
	}
	public boolean isQuote() {
		return quote;
	}
	public void setQuote(boolean quote) {
		this.quote = quote;
	}
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getJobCronTime() {
		return jobCronTime;
	}
	public void setJobCronTime(String jobCronTime) {
		this.jobCronTime = jobCronTime;
	}
	public String getProviderEmail() {
		return providerEmail;
	}
	public void setProviderEmail(String providerEmail) {
		this.providerEmail = providerEmail;
	}
	public OfferDeliveryType getOfferDeliveryType() {
		return offerDeliveryType;
	}
	public void setOfferDeliveryType(OfferDeliveryType offerDeliveryType) {
		this.offerDeliveryType = offerDeliveryType;
	}
	public OfferDeliveryFileFormat getOfferDeliveryFileFormat() {
		return offerDeliveryFileFormat;
	}
	public void setOfferDeliveryFileFormat(OfferDeliveryFileFormat offerDeliveryFileFormat) {
		this.offerDeliveryFileFormat = offerDeliveryFileFormat;
	}
	public OfferDeliveryFileStorageType getOfferDeliveryFileStorageType() {
		return offerDeliveryFileStorageType;
	}
	public void setOfferDeliveryFileStorageType(OfferDeliveryFileStorageType offerDeliveryFileStorageType) {
		this.offerDeliveryFileStorageType = offerDeliveryFileStorageType;
	}

}