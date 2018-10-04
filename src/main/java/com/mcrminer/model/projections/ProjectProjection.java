package com.mcrminer.model.projections;

import java.util.Set;

public interface ProjectProjection {
    Long getId();
    String getUrlPath();
    String getName();
    Set<ReviewRequestProjection> getReviewRequests();
}
