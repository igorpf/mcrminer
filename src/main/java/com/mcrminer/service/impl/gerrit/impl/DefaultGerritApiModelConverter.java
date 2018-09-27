package com.mcrminer.service.impl.gerrit.impl;

import com.google.gerrit.extensions.client.ChangeStatus;
import com.google.gerrit.extensions.common.*;
import com.mcrminer.model.*;
import com.mcrminer.model.enums.FileStatus;
import com.mcrminer.model.enums.ReviewRequestStatus;
import com.mcrminer.service.impl.gerrit.GerritApiModelConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultGerritApiModelConverter implements GerritApiModelConverter {

    private static final String CODE_REVIEW_LABEL = "Code-Review";
    private static final Map<ChangeStatus, ReviewRequestStatus> map = new EnumMap<>(ChangeStatus.class);
    static {
        map.put(ChangeStatus.MERGED, ReviewRequestStatus.MERGED);
        map.put(ChangeStatus.NEW, ReviewRequestStatus.PENDING);
        map.put(ChangeStatus.DRAFT, ReviewRequestStatus.PENDING);
        map.put(ChangeStatus.SUBMITTED, ReviewRequestStatus.PENDING);
        map.put(ChangeStatus.ABANDONED, ReviewRequestStatus.REJECTED);
    }
    private static final Map<Character, FileStatus> fileStatusMap = new HashMap<>();
    static {
        fileStatusMap.put('A', FileStatus.ADDED);
        fileStatusMap.put('D', FileStatus.DELETED);
        fileStatusMap.put('C', FileStatus.COPIED);
        fileStatusMap.put('R', FileStatus.MOVED);
        fileStatusMap.put('W', FileStatus.MODIFIED);
    }

    @Resource(name = "gerritDefaultLabels")
    private Set<ApprovalStatus> gerritDefaultLabels;

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
        return changeInfos
                .stream()
                .map(this::reviewsFromChange)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Review> reviewsFromChange(ChangeInfo changeInfo) {
        List<ApprovalInfo> approvals = changeInfo.labels.get(CODE_REVIEW_LABEL).all;
        return approvals
                .stream()
                .map(this::fromApproval)
                .collect(Collectors.toList());
    }

    private Review fromApproval(ApprovalInfo approvalInfo) {
        Review review = new Review();
        fromValue(approvalInfo.value).ifPresent(review::setStatus);
        review.setAuthor(fromAccount(approvalInfo));
        return review;
    }

    private FileStatus fromCharacter(Character character) {
        return character != null? fileStatusMap.get(character) : FileStatus.MODIFIED;
    }

   private Optional<ApprovalStatus> fromValue(Integer value) {
        return gerritDefaultLabels
                .stream()
                .filter(label -> label.getValue().equals(value))
                .findFirst();
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
                .map(entry -> fromFile(entry.getValue(), entry.getKey()))
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
        file.setStatus(fromCharacter(fileInfo.status));
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
