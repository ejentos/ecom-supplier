package com.tsa.supplier.service.impl;

import com.tsa.supplier.service.entity.Base;
import com.tsa.supplier.service.CRUD;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceAbstract<T extends Base, V extends com.tsa.supplier.data.dao.api.CRUD<T>> implements CRUD<T> {

    @Autowired
    protected V dao;

    @Override
    public T get(long id) {
        return dao.get(id);
    }

    @Override
    public T insert(T object) {
        return dao.insert(object);
    }

    @Override
    public void update(T object) {
        dao.update(object);
    }

    @Override
    public T merge(T object) {
        if(object != null) {
            if(this.get(object.getId()) == null) {
                object = this.insert(object);
            } else {
                this.update(object);
            }
        }
        return object;
    }

    @Override
    public void delete(T object) {
        dao.delete(object.getId());
    }

}