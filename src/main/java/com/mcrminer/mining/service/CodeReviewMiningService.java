package com.mcrminer.mining.service;

import com.mcrminer.persistence.model.Project;

public interface CodeReviewMiningService {
    Project fetchProject(String projectId, AuthenticationData authData);
}
