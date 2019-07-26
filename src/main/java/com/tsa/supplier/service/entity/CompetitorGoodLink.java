package com.tsa.supplier.service.entity;

import java.math.BigDecimal;

public class CompetitorGoodLink {

	private long id;
	private long competitorId;
	private long goodId;
	private boolean active;
	private String url;
	private BigDecimal lowPrice;
	private BigDecimal highPrice;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(long competitorId) {
		this.competitorId = competitorId;
	}
	public long getGoodId() {
		return goodId;
	}
	public void setGoodId(long goodId) {
		this.goodId = goodId;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public BigDecimal getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}
	public BigDecimal getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}

}