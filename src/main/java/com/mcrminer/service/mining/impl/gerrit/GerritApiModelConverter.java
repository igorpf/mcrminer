package com.mcrminer.service.mining.impl.gerrit;

import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.CommentInfo;
import com.google.gerrit.extensions.common.ProjectInfo;
import com.mcrminer.persistence.model.Diff;
import com.mcrminer.persistence.model.Project;
import com.mcrminer.persistence.model.Review;
import com.mcrminer.persistence.model.ReviewRequest;

import java.util.List;
import java.util.Map;

public interface GerritApiModelConverter {
    Project fromProject(ProjectInfo gerritProject);
    List<ReviewRequest> reviewRequestsFromChanges(List<ChangeInfo> changeInfos);
    List<Diff> diffsFromChanges(List<ChangeInfo> changeInfos, Map<ChangeInfo, Map<String, List<CommentInfo>>> changeInfoCommentsMap);
    List<Review> reviewsFromChanges(List<ChangeInfo> changeInfos);
}
