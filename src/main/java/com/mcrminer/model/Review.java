package com.mcrminer.model;

import com.mcrminer.model.enums.ApprovalStatus;

public final class Review extends BaseAuditingEntity {

    private User author;
    private PullRequest pullRequest;

    private ApprovalStatus status;
    private String description;
}
