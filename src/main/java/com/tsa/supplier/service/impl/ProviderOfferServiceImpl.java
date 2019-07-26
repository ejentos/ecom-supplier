package com.tsa.supplier.service.impl;

import com.tsa.supplier.data.dao.api.IProviderOfferDAO;
import com.tsa.supplier.service.entity.ProviderOffer;
import com.tsa.supplier.service.IProviderOfferService;
import org.springframework.stereotype.Service;

@Service
public class ProviderOfferServiceImpl extends ServiceAbstract<ProviderOffer, IProviderOfferDAO> implements IProviderOfferService {

    @Override
    public void setStalePriceFlag(long providerId) {
        dao.setStalePriceFlag(providerId);
    }

}
