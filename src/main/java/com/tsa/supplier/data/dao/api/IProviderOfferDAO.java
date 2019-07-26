package com.tsa.supplier.data.dao.api;

import com.tsa.supplier.service.entity.ProviderOffer;

import java.util.List;

public interface IProviderOfferDAO extends CRUD<ProviderOffer> {

	List<ProviderOffer> getProviderOffersByGoodId(long goodId);

	List<ProviderOffer> getProviderOffersByArticleId(long articleId);

	int[] insertBatch(List<ProviderOffer> offers);

	int[] updateBatch(List<ProviderOffer> offers);

	void deleteAllByProviderId(long providerId);

	void deleteByGoodId(long id);

	void setStalePriceFlag(long providerId);

}