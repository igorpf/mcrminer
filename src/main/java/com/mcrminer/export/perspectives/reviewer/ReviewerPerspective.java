package com.mcrminer.export.perspectives.reviewer;

import lombok.Data;

@Data
public class ReviewerPerspective {
    String email, fullname, username;
    long comments, reviews, vetos, approvals;
}