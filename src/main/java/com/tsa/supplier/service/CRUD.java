package com.tsa.supplier.service;

public interface CRUD<T> {

    T get(long id);

    T insert(T object);

    void update(T object);

    T merge(T object);

    void delete(T object);

}