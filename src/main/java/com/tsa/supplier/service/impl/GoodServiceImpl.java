package com.tsa.supplier.service.impl;

import com.tsa.supplier.data.dao.api.IGoodDAO;
import com.tsa.supplier.service.entity.Good;
import com.tsa.supplier.service.IGoodService;
import org.springframework.stereotype.Service;

@Service
public class GoodServiceImpl extends ServiceAbstract<Good, IGoodDAO> implements IGoodService {

}