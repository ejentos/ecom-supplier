package com.tsa.supplier.data.dao.api;

import java.util.List;

public interface CRUD<T> {

	T get(long id);

	T insert(T object);

	void update(T object);

	T merge(T object);
	
	void delete(long id);
	
	List<T> getAll();

}