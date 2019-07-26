package com.tsa.supplier.service.entity;

public class Article extends Base {

	private long goodId;
	private long brandId;
	private String article;
	private String manufactureCountry;

	public long getGoodId() {
		return goodId;
	}
	public void setGoodId(long goodId) {
		this.goodId = goodId;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getManufactureCountry() {
		return manufactureCountry;
	}
	public void setManufactureCountry(String manufactureCountry) {
		this.manufactureCountry = manufactureCountry;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
}