package com.tsa.supplier.service.impl;

import com.tsa.supplier.data.dao.api.ICategoryDAO;
import com.tsa.supplier.service.entity.Category;
import com.tsa.supplier.service.ICategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceAbstract<Category, ICategoryDAO> implements ICategoryService {

}