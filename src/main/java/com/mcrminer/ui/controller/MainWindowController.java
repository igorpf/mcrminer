package com.mcrminer.ui.controller;


import com.mcrminer.ui.controller.localization.LocalizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainWindowController {

    private static final Logger LOG = LoggerFactory.getLogger(MainWindowController.class);
    private LocalizationService localizationService;

    @Autowired
    public void setLocalizationService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }
}
