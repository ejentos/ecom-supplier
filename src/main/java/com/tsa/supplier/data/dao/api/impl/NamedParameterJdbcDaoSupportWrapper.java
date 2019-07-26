package com.tsa.supplier.data.dao.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

public abstract class NamedParameterJdbcDaoSupportWrapper extends NamedParameterJdbcDaoSupport {

    @Autowired
    protected DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

}