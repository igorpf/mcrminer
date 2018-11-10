package com.mcrminer.ui.localization.impl;

import com.mcrminer.ui.localization.LocalizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class DefaultLocalizationService implements LocalizationService {

    private ResourceBundle BUNDLE;

    @Value("${mcrminer.locale:en}")
    private String localeCode;

    @PostConstruct
    public void init() {
        BUNDLE = ResourceBundle.getBundle("bundles/messages", Locale.forLanguageTag(localeCode));
    }

    @Override
    public String getMessage(String key, Object... args) {
        MessageFormat formatter = new MessageFormat(BUNDLE.getString(key));
        return formatter.format(args);
    }
}
