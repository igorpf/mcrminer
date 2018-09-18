package com.mcrminer.model;

import com.mcrminer.model.enums.ReviewRequestStatus;

import java.util.Set;

public final class ReviewRequest extends Reviewable {

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
    private ReviewRequestStatus status;
    private String description;


}
