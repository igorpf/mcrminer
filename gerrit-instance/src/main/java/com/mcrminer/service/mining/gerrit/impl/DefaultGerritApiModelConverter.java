package com.mcrminer.service.mining.impl.gerrit.impl;

import com.google.gerrit.extensions.client.ChangeStatus;
import com.google.gerrit.extensions.common.*;
import com.mcrminer.persistence.model.*;
import com.mcrminer.persistence.model.enums.FileStatus;
import com.mcrminer.persistence.model.enums.ReviewRequestStatus;
import com.mcrminer.service.mining.impl.gerrit.GerritApiModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultGerritApiModelConverter implements GerritApiModelConverter {

    private static final String CODE_REVIEW_LABEL = "Code-Review";
    private static final Map<ChangeStatus, ReviewRequestStatus> map = new EnumMap<>(ChangeStatus.class);
    private static final Map<Character, FileStatus> fileStatusMap = new HashMap<>();
    static {
        map.put(ChangeStatus.MERGED, ReviewRequestStatus.MERGED);
        map.put(ChangeStatus.NEW, ReviewRequestStatus.PENDING);
        map.put(ChangeStatus.DRAFT, ReviewRequestStatus.PENDING);
        map.put(ChangeStatus.SUBMITTED, ReviewRequestStatus.PENDING);
        map.put(ChangeStatus.ABANDONED, ReviewRequestStatus.REJECTED);
        fileStatusMap.put('A', FileStatus.ADDED);
        fileStatusMap.put('D', FileStatus.DELETED);
        fileStatusMap.put('C', FileStatus.COPIED);
        fileStatusMap.put('R', FileStatus.MOVED);
        fileStatusMap.put('W', FileStatus.MODIFIED);
    }
    private Set<ApprovalStatus> gerritDefaultLabels;

    @Autowired
    public DefaultGerritApiModelConverter(@Qualifier("gerritDefaultLabels") Set<ApprovalStatus> gerritDefaultLabels) {
        this.gerritDefaultLabels = gerritDefaultLabels;
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
    public List<Diff> diffsFromChanges(List<ChangeInfo> changeInfos, Map<ChangeInfo, Map<String, List<CommentInfo>>> changeInfoCommentsMap) {
        return changeInfos
                .stream()
                .map(changeInfo -> diffsFromChange(changeInfo, changeInfoCommentsMap.get(changeInfo)))
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
        return approvals != null? approvals
                .stream()
                .map(this::fromApproval)
                .collect(Collectors.toList()) : Collections.emptyList();
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

    private List<Diff> diffsFromChange(ChangeInfo changeInfo, Map<String, List<CommentInfo>> changeInfoCommentsMap) {
        return changeInfo.revisions
                .entrySet()
                .stream()
                .map(entry -> fromRevision(entry.getValue(), changeInfoCommentsMap))
                .collect(Collectors.toList());
    }

    private Diff fromRevision(RevisionInfo revisionInfo, Map<String, List<CommentInfo>> changeInfoCommentsMap) {
        final Diff diff = new Diff();
        diff.setCreatedTime(revisionInfo.created.toLocalDateTime());
        List<File> files = revisionInfo.files
                .entrySet()
                .stream()
                .map(entry -> fromFile(entry.getValue(), entry.getKey(), changeInfoCommentsMap.get(entry.getKey())))
                .collect(Collectors.toList());

        diff.setFiles(files);
        return diff;
    }

    private File fromFile(FileInfo fileInfo, String currentFilename, List<CommentInfo> comments) {
        File file = new File();
        file.setNewFilename(currentFilename);
        file.setOldFilename(fileInfo.oldPath);
        if (fileInfo.linesInserted != null)
            file.setLinesInserted(Long.valueOf(fileInfo.linesInserted));
        if (fileInfo.linesDeleted != null)
            file.setLinesRemoved(Long.valueOf(fileInfo.linesDeleted));
        file.setStatus(fromCharacter(fileInfo.status));
        if (comments != null)
            file.setComments(comments.stream().map(this::fromComment).collect(Collectors.toList()));
        return file;
    }

    private Comment fromComment(CommentInfo commentInfo) {
        Comment comment = new Comment();
        comment.setAuthor(fromAccount(commentInfo.author));
        comment.setText(commentInfo.message);
        comment.setUpdatedTime(commentInfo.updated.toLocalDateTime());
        return comment;
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
