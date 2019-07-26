package com.tsa.supplier.service.impl;

import com.tsa.supplier.data.dao.api.IProviderDAO;
import com.tsa.supplier.service.entity.Provider;
import com.tsa.supplier.service.IProviderService;
import org.springframework.stereotype.Service;

@Service
public class ProviderService extends ServiceAbstract<Provider, IProviderDAO> implements IProviderService {

}