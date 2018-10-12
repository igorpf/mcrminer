package com.mcrminer.export.perspectives.comment;

import com.mcrminer.model.enums.ReviewRequestStatus;
import lombok.Data;

@Data
public class CommentPerspective {
    Long id;
    String filename, comment;
    // Author attributes
    String authorEmail, authorFullname, authorUsername;
    // Review request submitter attributes
    String reviewRequestSubmitterEmail, reviewRequestSubmitterFullname, reviewRequestSubmitterUsername;
    // Review request attributes
    Long reviewRequestId;
    String branch, commitId, description;
    ReviewRequestStatus status;
    boolean isPublic;
    // Project attributes
    Long projectId;
    String codeReviewToolId, urlPath, name;

}
