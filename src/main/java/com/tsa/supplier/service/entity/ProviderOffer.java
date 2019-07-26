package com.tsa.supplier.service.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProviderOffer extends Base {

	private long providerId;
	private long articleId;
	private BigDecimal price;
	private int count;
	private String multiplicity;
	private Date delivery;

	/*
		specify whether price does need to be updated
		finally, all not updated offers should be removed since it is absent in offers
	*/
	private boolean actualPrice;

	// non-Transient
	private String article;
	// TODO brandName - to brand ID in article table probably lots of requests won't simply work
	private String brandName;
	
	public long getProviderId() {
		return providerId;
	}
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMultiplicity() {
		return multiplicity;
	}
	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}
	public Date getDelivery() {
		return delivery;
	}
	public void setDelivery(Date delivery) {
		this.delivery = delivery;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public void setDeliveryDate(String value) {
		if(value != null && !value.isEmpty()) {
			try {
				delivery = new SimpleDateFormat("yyyy-MM-dd").parse(value);
			} catch(ParseException e) {
				delivery = null;
			}
		}
	}
	public boolean isActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(boolean actualPrice) {
		this.actualPrice = actualPrice;
	}
}