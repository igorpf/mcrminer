package com.mcrminer.ui.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainWindowController {

    private static final Logger LOG = LoggerFactory.getLogger(MainWindowController.class);

    @Autowired
    public MainWindowController() {
        LOG.info("Application started up successfully");
    }
}
