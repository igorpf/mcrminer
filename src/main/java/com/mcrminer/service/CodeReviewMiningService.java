package com.mcrminer.service;

import com.mcrminer.model.Project;

public interface CodeReviewMiningService {
    Project fetchProject(String host, String projectId, AuthenticationData authData);
}
