package com.mcrminer.mining.export.perspectives.reviewable;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewablePerspective {
    Long id, files, reviews, comments, vetos, approvals;
    String branch, status;
    LocalDateTime createdTime, updatedTime;
    // Project attributes
    Long projectId;
    String codeReviewToolId, urlPath, name;
}
