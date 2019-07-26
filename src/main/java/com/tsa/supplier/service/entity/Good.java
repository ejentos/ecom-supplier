package com.tsa.supplier.service.entity;

import java.math.BigDecimal;

public class Good extends Base {

	private long brandId;
	private long categoryId;
	private String name;
	private BigDecimal price;
	private BigDecimal priceRaw;

	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPriceRaw() {
		return priceRaw;
	}

	public void setPriceRaw(BigDecimal priceRaw) {
		this.priceRaw = priceRaw;
	}
}