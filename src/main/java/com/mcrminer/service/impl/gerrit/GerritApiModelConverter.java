package com.mcrminer.service.impl.gerrit;

import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.ProjectInfo;
import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;

import java.util.List;

public interface GerritApiModelConverter {
    Project fromProject(ProjectInfo gerritProject);
    List<ReviewRequest> fromChanges(List<ChangeInfo> changeInfos);
}
