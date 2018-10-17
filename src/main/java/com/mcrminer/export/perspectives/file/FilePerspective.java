package com.mcrminer.export.perspectives.file;

import com.mcrminer.model.enums.FileStatus;
import com.mcrminer.model.enums.ReviewRequestStatus;
import lombok.Data;

@Data
public class FilePerspective {
    Long id;
    String newFilename, oldFilename;
    Long linesInserted, linesRemoved;
    FileStatus fileStatus;
    // Author attributes
    String authorEmail, authorFullname, authorUsername;
    // Review request submitter attributes
    String reviewRequestSubmitterEmail, reviewRequestSubmitterFullname, reviewRequestSubmitterUsername;
    // Review request attributes
    Long reviewRequestId;
    String branch, commitId, description;
    ReviewRequestStatus reviewRequestStatus;
    boolean isPublic;
    // Project attributes
    Long projectId;
    String codeReviewToolId, urlPath, name;
}
