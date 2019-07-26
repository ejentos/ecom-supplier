package com.tsa.supplier.service;

import com.tsa.supplier.service.entity.ProviderPriceUploadSettings;

import java.util.List;

public interface IProviderPriceUploadSettingsService extends CRUD<ProviderPriceUploadSettings> {

    List<ProviderPriceUploadSettings> getSchedulingProviderSettings();

    ProviderPriceUploadSettings getByProviderId(long providerId);

}
