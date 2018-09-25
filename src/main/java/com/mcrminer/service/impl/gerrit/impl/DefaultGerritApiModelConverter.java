package com.mcrminer.service.impl.gerrit.impl;

import com.google.gerrit.extensions.client.ChangeStatus;
import com.google.gerrit.extensions.common.AccountInfo;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.ProjectInfo;
import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.model.User;
import com.mcrminer.model.enums.ReviewRequestStatus;
import com.mcrminer.service.impl.gerrit.GerritApiModelConverter;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultGerritApiModelConverter implements GerritApiModelConverter {

    private static final Map<ChangeStatus, ReviewRequestStatus> map = new EnumMap<>(ChangeStatus.class);
    static {
        map.put(ChangeStatus.MERGED, ReviewRequestStatus.MERGED);
        map.put(ChangeStatus.NEW, ReviewRequestStatus.PENDING);
        map.put(ChangeStatus.DRAFT, ReviewRequestStatus.PENDING);
        map.put(ChangeStatus.SUBMITTED, ReviewRequestStatus.PENDING);
        map.put(ChangeStatus.ABANDONED, ReviewRequestStatus.REJECTED);
    }

    @Override
    public Project fromProject(ProjectInfo gerritProject) {
        Project project = new Project();
        project.setName(gerritProject.name);
        project.setCodeReviewToolId(gerritProject.id);
        return project;
    }

    @Override
    public List<ReviewRequest> fromChanges(List<ChangeInfo> changeInfos) {
        return changeInfos
                .stream()
                .map(this::fromChange)
                .collect(Collectors.toList());
    }
    private ReviewRequest fromChange(ChangeInfo changeInfo) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setBranch(changeInfo.branch);
        reviewRequest.setPublic(!changeInfo.isPrivate);
        reviewRequest.setSubmitter(fromAccount(changeInfo.owner));
        reviewRequest.setStatus(fromStatus(changeInfo.status));
        reviewRequest.setDescription(changeInfo.subject);
        return reviewRequest;
    }

    private User fromAccount(AccountInfo accountInfo) {
        User user = new User();
        user.setEmail(accountInfo.email);
        user.setFullname(accountInfo.name);
        user.setUsername(accountInfo.username);
        return user;
    }

    private ReviewRequestStatus fromStatus(ChangeStatus status) {
        return map.get(status);
    }
}
