package com.mcrminer.repository.factory;

import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;

public interface DomainObjectsFactory {

    Project createProject(String urlPath, String name);

    ReviewRequest createReviewRequest(Project project);
}
