package com.mcrminer.service.impl.gerrit.impl;

import com.google.gerrit.extensions.client.ChangeStatus;
import com.google.gerrit.extensions.common.*;
import com.mcrminer.model.*;
import com.mcrminer.model.enums.ReviewRequestStatus;
import com.mcrminer.service.impl.gerrit.GerritApiModelConverter;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public List<ReviewRequest> reviewRequestsFromChanges(List<ChangeInfo> changeInfos) {
        return changeInfos
                .stream()
                .map(this::reviewRequestFromChange)
                .collect(Collectors.toList());
    }

    @Override
    public List<Diff> diffsFromChanges(List<ChangeInfo> changeInfos) {
        return changeInfos
                .stream()
                .map(this::diffsFromChange)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> reviewsFromChanges(List<ChangeInfo> changeInfos) {
        return Collections.emptyList();
    }

    private ReviewRequest reviewRequestFromChange(ChangeInfo changeInfo) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setBranch(changeInfo.branch);
        reviewRequest.setCommitId(changeInfo.changeId);
        reviewRequest.setCreatedTime(changeInfo.created.toLocalDateTime());
        reviewRequest.setUpdatedTime(changeInfo.updated.toLocalDateTime());
        reviewRequest.setPublic(changeInfo.isPrivate != null && !changeInfo.isPrivate);
        reviewRequest.setSubmitter(fromAccount(changeInfo.owner));
        reviewRequest.setStatus(fromStatus(changeInfo.status));
        reviewRequest.setDescription(changeInfo.subject);
        return reviewRequest;
    }

    private List<Diff> diffsFromChange(ChangeInfo changeInfo) {
        return changeInfo.revisions
                .entrySet()
                .stream()
                .map(entry -> fromRevision(entry.getValue()))
                .collect(Collectors.toList());
    }

    private Diff fromRevision(RevisionInfo revisionInfo) {
        final Diff diff = new Diff();
        diff.setCreatedTime(revisionInfo.created.toLocalDateTime());
        List<File> files = revisionInfo.files
                .entrySet()
                .stream()
                .map(entry -> {
                    File f = fromFile(entry.getValue(), entry.getKey());
                    f.setDiff(diff);
                    return f;
                })
                .collect(Collectors.toList());

        diff.setFiles(files);
        return diff;
    }

    private File fromFile(FileInfo fileInfo, String currentFilename) {
        File file = new File();
        file.setNewFilename(currentFilename);
        file.setOldFilename(fileInfo.oldPath);
        file.setLinesInserted(fileInfo.linesInserted);
        file.setLinesRemoved(fileInfo.linesDeleted);
//        file.setStatus(fileInfo.status);
        return file;
    }

    private User fromAccount(AccountInfo accountInfo) {
        if (accountInfo.email == null)
            return null;
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
