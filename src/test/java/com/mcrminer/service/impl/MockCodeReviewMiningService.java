package com.mcrminer.service.impl;

import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.service.AuthenticationData;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("mockCodeReviewMiningService")
public class MockCodeReviewMiningService extends AbstractCodeReviewMiningService {

    @Override
    protected Project getProject(String host, String projectId, AuthenticationData authData) {
        Project project = new Project();
        project.setUrlPath(String.format("%s/%s", host, projectId));
        project.setName("Mock project");
        return project;
    }

    @Override
    protected List<ReviewRequest> getReviewRequestsForProject(Project project, AuthenticationData authData) {
        ReviewRequest reviewRequest1 = new ReviewRequest();
        ReviewRequest reviewRequest2 = new ReviewRequest();
        return Arrays.asList(reviewRequest1, reviewRequest2);
    }
}
