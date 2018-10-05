package com.mcrminer.ui.controller;


import com.mcrminer.service.AuthenticationData;
import com.mcrminer.service.CodeReviewMiningService;
import com.mcrminer.service.DefaultAuthenticationData;
import com.mcrminer.ui.controller.localization.LocalizationService;
import com.mcrminer.ui.tasks.FetchProjectTask;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MainWindowController {

    private static final Logger LOG = LoggerFactory.getLogger(MainWindowController.class);
    private LocalizationService localizationService;
    private CodeReviewMiningService codeReviewMiningService;

    @FXML
    private TextField projectUrl, projectName, username, password;
    @FXML
    private Button searchButton;

    public void fetchProject() {
        AuthenticationData auth = new DefaultAuthenticationData(username.getText(), password.getText(), projectUrl.getText());
        FetchProjectTask task = new FetchProjectTask(codeReviewMiningService, projectName.getText(), auth);
        task.setOnRunning((runningEvent) -> {
            LOG.info("Fetching project");
            searchButton.setDisable(true);
        });

        task.setOnSucceeded((succeedEvent) -> {
            LOG.info("Succeeded fetching project");
            searchButton.setDisable(false);
        });
        task.setOnFailed((failedEvent) -> {
            LOG.error("Error fetching project", failedEvent.getSource().getException());
            searchButton.setDisable(false);
        });
        ExecutorService executorService
                = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();
    }

    public LocalizationService getLocalizationService() {
        return localizationService;
    }

    @Autowired
    public void setLocalizationService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    public CodeReviewMiningService getCodeReviewMiningService() {
        return codeReviewMiningService;
    }

    @Resource(name = "gerritCodeReviewMiningService")
    public void setCodeReviewMiningService(CodeReviewMiningService codeReviewMiningService) {
        this.codeReviewMiningService = codeReviewMiningService;
    }
}
