package com.mcrminer.model;

import com.mcrminer.model.enums.PullRequestStatus;

import java.util.Set;

public final class PullRequest extends BaseAuditingEntity {

    // Relations
    private User submitter;
    private Set<Diff> diffs;
    private Set<Review> reviews;
    private Project project;

    //
    private Integer id;
    private String branch;
    private String commitId;
    private boolean isPublic;
    private PullRequestStatus status;
    private String description;


}
