package com.mcrminer.repository.factory;

import com.mcrminer.model.*;

public interface DomainObjectsFactory {
    Comment createComment(String text, File file);
    Comment createComment(String text);
    Diff createDiff(ReviewRequest reviewRequest);
    File createFile(String filename, Diff diff);
    Project createProject(String urlPath, String name);
    Review createReview(String description, Reviewable reviewed);
    ReviewRequest createReviewRequest(Project project);
    User createUser(String email);
}
