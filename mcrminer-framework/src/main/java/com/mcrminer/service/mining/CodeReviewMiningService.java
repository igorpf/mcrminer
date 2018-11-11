package com.mcrminer.service.mining;

import com.mcrminer.persistence.model.Project;

public interface CodeReviewMiningService {
    Project fetchProject(String projectId, AuthenticationData authData);
    String getToolName();
}
