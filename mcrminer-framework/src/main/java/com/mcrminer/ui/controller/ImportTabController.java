package com.mcrminer.ui.controller;

import com.mcrminer.service.mining.AuthenticationData;
import com.mcrminer.service.mining.CodeReviewMiningService;
import com.mcrminer.service.mining.DefaultAuthenticationData;
import com.mcrminer.ui.localization.LocalizationService;
import com.mcrminer.ui.tasks.FetchProjectTask;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class ImportTabController {
    private static final Logger LOG = LoggerFactory.getLogger(ImportTabController.class);

    private LocalizationService localizationService;
    private List<CodeReviewMiningService> codeReviewMiningServices;

    @FXML
    private TextField projectUrl, projectName, username, password;
    @FXML
    private Label totalTime;
    @FXML
    private Button searchButton;
    @FXML
    private ComboBox<MiningServiceChoice> miningServiceChoice;

    @FXML
    public void initialize() {
        List<MiningServiceChoice> choices = getCodeReviewMiningServices()
                .stream()
                .map(MiningServiceChoice::new)
                .collect(Collectors.toList());
        miningServiceChoice.setItems(FXCollections.observableList(choices));
        miningServiceChoice.getSelectionModel().select(0);
    }

    public void fetchProject() {
        AuthenticationData auth = new DefaultAuthenticationData(username.getText(), password.getText(), projectUrl.getText());
        FetchProjectTask task = new FetchProjectTask(miningServiceChoice.getSelectionModel().getSelectedItem().getCodeReviewMiningService(), projectName.getText(), auth);

        final Instant now = Instant.now();
        task.setOnRunning((runningEvent) -> {
            LOG.info("Fetching project");
            searchButton.setDisable(true);
            totalTime.setText(getLocalizationService().getMessage("tab.import.label.fetch.running"));
        });

        task.setOnSucceeded((succeedEvent) -> {
            LOG.info("Succeeded fetching project.");
            searchButton.setDisable(false);
            long totalSeconds = Duration.between(now, Instant.now()).getSeconds();
            totalTime.setText(getTotalSecondsMessage(totalSeconds));
        });
        task.setOnFailed((failedEvent) -> {
            LOG.error("Error fetching project", failedEvent.getSource().getException());
            searchButton.setDisable(false);
            totalTime.setText(getLocalizationService().getMessage("tab.import.label.fetch.error", failedEvent.getSource().getException().getMessage()));
        });
        ExecutorService executorService
                = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();
    }

    private String getTotalSecondsMessage(long totalSeconds) {
        return getLocalizationService().getMessage("tab.import.time.total", totalSeconds);
    }

    public List<CodeReviewMiningService> getCodeReviewMiningServices() {
        return codeReviewMiningServices;
    }

    public LocalizationService getLocalizationService() {
        return localizationService;
    }

    @Autowired
    public void setLocalizationService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @Autowired
    public void setCodeReviewMiningServices(List<CodeReviewMiningService> codeReviewMiningServices) {
        this.codeReviewMiningServices = codeReviewMiningServices;
    }
}

class MiningServiceChoice {
    private CodeReviewMiningService codeReviewMiningService;
    private String name;

    MiningServiceChoice(CodeReviewMiningService codeReviewMiningService) {
        this.codeReviewMiningService = codeReviewMiningService;
        this.name = codeReviewMiningService.getToolName();
    }

    public CodeReviewMiningService getCodeReviewMiningService() {
        return codeReviewMiningService;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}