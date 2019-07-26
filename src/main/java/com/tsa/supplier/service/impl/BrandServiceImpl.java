package com.tsa.supplier.service.impl;

import com.tsa.supplier.data.dao.api.IBrandDAO;
import com.tsa.supplier.service.entity.Brand;
import com.tsa.supplier.service.IBrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends ServiceAbstract<Brand, IBrandDAO> implements IBrandService {

}