package com.mcrminer.repository.factory.impl;

import com.mcrminer.model.*;
import com.mcrminer.model.enums.FileStatus;
import com.mcrminer.model.enums.ReviewRequestStatus;
import com.mcrminer.repository.factory.DomainObjectsFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

public class TransientObjectsFactory implements DomainObjectsFactory {
    @Override
    public Project createProject(String urlPath, String name) {
        Project project = new Project();
        project.setUrlPath(urlPath);
        project.setName(name);
        project.setCodeReviewToolId("gerrit");
        project.setId(1L);
        return project;
    }

    @Override
    public Review createReview(String description, Reviewable reviewed) {
        Review review = new Review();
        review.setId(1L);
        review.setReviewed(reviewed);
        review.setAuthor(createUser("review@example.com"));
        review.setDescription(description);
        review.setCreatedTime(LocalDateTime.now().minusWeeks(1));
        review.setUpdatedTime(LocalDateTime.now());
        review.setStatus(createApprovalStatus());
        return review;
    }

    @Override
    public ReviewRequest createReviewRequest(Project project) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setProject(project);
        reviewRequest.setId(1L);
        reviewRequest.setDiffs(Collections.singleton(createDiff(reviewRequest)));
        reviewRequest.setUpdatedTime(LocalDateTime.now());
        reviewRequest.setCreatedTime(LocalDateTime.now().minusMonths(2));
        reviewRequest.setDescription("new software version");
        reviewRequest.setSubmitter(createUser("user@user.com"));
        reviewRequest.setCommitId("0xdeadbeef");
        reviewRequest.setPublic(true);
        reviewRequest.setStatus(ReviewRequestStatus.MERGED);
        reviewRequest.setBranch("master");
        return reviewRequest;
    }

    @Override
    public Comment createComment(String text, File file) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(createUser("user@example.com"));
        comment.setFile(file);
        comment.setId(1L);
        return comment;
    }

    @Override
    public Comment createComment(String text) {
        Project project = createProject("http://default.com", "transient");
        ReviewRequest reviewRequest = createReviewRequest(project);
        Diff diff = createDiff(reviewRequest);
        File file = createFile("somefile", diff);
        return createComment(text, file);
    }

    @Override
    public Diff createDiff(ReviewRequest reviewRequest) {
        Diff diff = new Diff();
        diff.setId(2L);
        diff.setReviewRequest(reviewRequest);
        diff.setCreatedTime(LocalDateTime.now().minusDays(2));
        diff.setUpdatedTime(LocalDateTime.now());
        diff.setFiles(Arrays.asList(
                createFile("somefile.txt", diff),
                createFile("anotherfile.java", diff)
        ));
        diff.setReviews(Collections.singleton(
                createReview("cool changes", diff)
        ));
        return diff;
    }

    @Override
    public User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setFullname("transient user");
        user.setUsername("someusername");
        return user;
    }

    @Override
    public File createFile(String filename, Diff diff) {
        File file = new File();
        file.setId(2L);
        file.setLinesInserted(1L);
        file.setLinesRemoved(2L);
        file.setStatus(FileStatus.MODIFIED);
        file.setNewFilename(filename);
        file.setComments(Arrays.asList(
                createComment("nice feature", file),
                createComment("this is not ok", file)
        ));
        file.setDiff(diff);
        return file;
    }

    private ApprovalStatus createApprovalStatus() {
        return new ApprovalStatus("label", "cool", 2, true, false);
    }
}
