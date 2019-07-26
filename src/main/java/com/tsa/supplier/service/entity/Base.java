package com.tsa.supplier.service.entity;

public abstract class Base extends AuditEntity {

    private long id;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

}
