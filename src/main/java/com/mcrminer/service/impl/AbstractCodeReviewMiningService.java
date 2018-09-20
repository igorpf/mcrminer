package com.mcrminer.service.impl;

import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.repository.ProjectRepository;
import com.mcrminer.repository.ReviewRequestRepository;
import com.mcrminer.service.AuthenticationData;
import com.mcrminer.service.CodeReviewMiningService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

public abstract class AbstractCodeReviewMiningService implements CodeReviewMiningService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ReviewRequestRepository reviewRequestRepository;

    @Override
    public Project fetchProject(String host, String projectId, AuthenticationData authData) {
        Project project = getProject(host, projectId, authData);
        project = projectRepository.save(project);
        List<ReviewRequest> reviewRequests = getReviewRequestsForProject(project, authData);
        for (ReviewRequest reviewRequest : reviewRequests) {
            reviewRequest.setProject(project);
            populateReviewRequest(reviewRequest, authData);
            reviewRequestRepository.save(reviewRequest);
        }
        project.setReviewRequests(new HashSet<>(reviewRequests));
        return projectRepository.save(project);
    }

    private void populateReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData) {

    }

    protected abstract Project getProject(String host, String projectId, AuthenticationData authData);
    protected abstract List<ReviewRequest> getReviewRequestsForProject(Project project, AuthenticationData authData);
}
