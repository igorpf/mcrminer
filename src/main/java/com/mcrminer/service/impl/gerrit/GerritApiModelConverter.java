package com.mcrminer.service.impl.gerrit;

import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.ProjectInfo;
import com.mcrminer.model.Diff;
import com.mcrminer.model.Project;
import com.mcrminer.model.Review;
import com.mcrminer.model.ReviewRequest;

import java.util.List;

public interface GerritApiModelConverter {
    Project fromProject(ProjectInfo gerritProject);
    List<ReviewRequest> reviewRequestsFromChanges(List<ChangeInfo> changeInfos);
    List<Diff> diffsFromChanges(List<ChangeInfo> changeInfos);
    List<Review> reviewsFromChanges(List<ChangeInfo> changeInfos);
}
