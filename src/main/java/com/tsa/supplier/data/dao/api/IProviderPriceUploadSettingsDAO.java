package com.tsa.supplier.data.dao.api;

import com.tsa.supplier.service.entity.ProviderPriceUploadSettings;

import java.util.List;

public interface IProviderPriceUploadSettingsDAO extends CRUD<ProviderPriceUploadSettings> {

    List<ProviderPriceUploadSettings> getSchedulingProviderSettings();

    ProviderPriceUploadSettings getByProviderId(long providerId);

}