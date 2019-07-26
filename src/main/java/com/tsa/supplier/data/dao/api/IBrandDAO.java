package com.tsa.supplier.data.dao.api;

import com.tsa.supplier.service.entity.Brand;

public interface IBrandDAO extends CRUD<Brand> {

	String getBrandNameByGoodId(long goodId);

}