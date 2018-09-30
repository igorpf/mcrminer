package com.mcrminer.repository.factory;

import com.mcrminer.model.*;

public interface DomainObjectsFactory {

    Project createProject(String urlPath, String name);
    ReviewRequest createReviewRequest(Project project);
    Comment createComment(String text);
    User createUser(String email);
    File createFile(String filename);
}
