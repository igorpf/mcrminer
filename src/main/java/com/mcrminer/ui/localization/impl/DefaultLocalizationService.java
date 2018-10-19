package com.mcrminer.ui.localization.impl;

import com.mcrminer.ui.localization.LocalizationService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class DefaultLocalizationService implements LocalizationService {

    private final ResourceBundle BUNDLE;

    public DefaultLocalizationService() {
        BUNDLE = ResourceBundle.getBundle("bundles/messages", Locale.getDefault());
    }

    @Override
    public String getMessage(String key, Object... args) {
        MessageFormat formatter = new MessageFormat(BUNDLE.getString(key));
        return formatter.format(args);
    }
}
