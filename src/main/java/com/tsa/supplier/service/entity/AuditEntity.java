package com.tsa.supplier.service.entity;

import java.util.Date;

public abstract class AuditEntity {

	private Date created;
	private Date updated;

	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}