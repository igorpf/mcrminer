package com.mcrminer.ui.tasks;

import com.mcrminer.model.Project;
import com.mcrminer.service.AuthenticationData;
import com.mcrminer.service.CodeReviewMiningService;
import javafx.concurrent.Task;

public class FetchProjectTask extends Task<Project> {
    private final CodeReviewMiningService codeReviewMiningService;
    private final String projectId;
    private final AuthenticationData authData;

    public FetchProjectTask(CodeReviewMiningService codeReviewMiningService, String projectId, AuthenticationData authData) {
        this.codeReviewMiningService = codeReviewMiningService;
        this.projectId = projectId;
        this.authData = authData;
    }

    @Override
    protected Project call() throws Exception {
        return codeReviewMiningService.fetchProject(projectId, authData);
    }
}
