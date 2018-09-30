package com.mcrminer.repository.factory.impl;

import com.mcrminer.model.*;
import com.mcrminer.repository.*;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("persistentObjectsDomainFactory")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultDomainObjectsFactory implements DomainObjectsFactory {

    private ProjectRepository projectRepository;
    private ReviewRequestRepository reviewRequestRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private FileRepository fileRepository;

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

    @Override
    public Comment createComment(String text) {
        return null;
    }

    @Override
    public User createUser(String email) {
        return null;
    }

    @Override
    public File createFile(String filename) {
        return null;
    }


}
