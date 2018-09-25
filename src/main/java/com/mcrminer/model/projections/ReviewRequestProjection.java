package com.mcrminer.model.projections;

import java.util.List;

public interface ReviewRequestProjection {
    String getBranch();
    String getDescription();
    String getCommitId();
    boolean isPublic();
    List<DiffProjection> getDiffs();
}
