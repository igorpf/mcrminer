package com.mcrminer.ui.controller.localization.impl;

import com.mcrminer.ui.controller.localization.LocalizationService;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class DefaultLocalizationService implements LocalizationService {

    private final ResourceBundle BUNDLE;

    public DefaultLocalizationService() {
        BUNDLE = ResourceBundle.getBundle("bundles/messages", Locale.getDefault());
    }

    @Override
    public String getMessage(String key) {
        return BUNDLE.getString(key);
    }
}
