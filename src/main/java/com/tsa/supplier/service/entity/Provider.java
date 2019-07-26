package com.tsa.supplier.service.entity;

public class Provider extends Base {

	private String name;
	private String priceEmail;
	private boolean active;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPriceEmail() {
		return priceEmail;
	}
	public void setPriceEmail(String priceEmail) {
		this.priceEmail = priceEmail;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

}