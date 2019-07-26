package com.tsa.supplier.service.impl;

import com.tsa.supplier.data.dao.api.IProviderPriceUploadSettingsDAO;
import com.tsa.supplier.service.entity.ProviderPriceUploadSettings;
import com.tsa.supplier.service.IProviderPriceUploadSettingsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderPriceUploadSettingsServiceImpl extends ServiceAbstract<ProviderPriceUploadSettings, IProviderPriceUploadSettingsDAO> implements IProviderPriceUploadSettingsService {

    @Override
    public List<ProviderPriceUploadSettings> getSchedulingProviderSettings() {
        return dao.getSchedulingProviderSettings();
    }

    @Override
    public ProviderPriceUploadSettings getByProviderId(long providerId) {
        // TODO CACHE
        return dao.getByProviderId(providerId);
    }

}