package com.mcrminer.model;

public final class Review extends BaseAuditingEntity {

    private User author;
    private Reviewable reviewed;

    private ApprovalStatus status;
    private String description;
}
