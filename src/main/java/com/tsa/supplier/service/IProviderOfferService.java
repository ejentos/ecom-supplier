package com.tsa.supplier.service;

import com.tsa.supplier.service.entity.ProviderOffer;

public interface IProviderOfferService extends CRUD<ProviderOffer> {

    void setStalePriceFlag(long providerId);

}
