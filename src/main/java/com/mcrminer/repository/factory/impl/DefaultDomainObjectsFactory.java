package com.mcrminer.repository.factory.impl;

import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.repository.ProjectRepository;
import com.mcrminer.repository.ReviewRequestRepository;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultDomainObjectsFactory implements DomainObjectsFactory {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ReviewRequestRepository reviewRequestRepository;

    @Override
    public Project createProject(String urlPath, String name) {
        Project newProject = new Project();
        newProject.setUrlPath(urlPath);
        newProject.setName(name);
        return projectRepository.save(newProject);
    }

    @Override
    public ReviewRequest createReviewRequest(Project project) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setProject(project);
        return reviewRequestRepository.save(reviewRequest);
    }


}
