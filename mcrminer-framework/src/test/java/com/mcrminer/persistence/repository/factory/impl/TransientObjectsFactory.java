package com.mcrminer.persistence.repository.factory.impl;

import com.mcrminer.persistence.model.*;
import com.mcrminer.persistence.model.enums.FileStatus;
import com.mcrminer.persistence.model.enums.ReviewRequestStatus;
import com.mcrminer.persistence.repository.factory.DomainObjectsFactory;

import java.time.LocalDateTime;

public class TransientObjectsFactory implements DomainObjectsFactory {

    private static long ID_GENERATOR = 0;

    @Override
    public Project createProject(String urlPath, String name) {
        Project project = new Project();
        project.setUrlPath(urlPath);
        project.setName(name);
        project.setCodeReviewToolId("gerrit");
        project.setId(getId());
        return project;
    }

    @Override
    public Review createReview(String description, Reviewable reviewed) {
        Review review = new Review();
        review.setId(getId());
        review.setReviewed(reviewed);
        review.setAuthor(createUser("review@example.com"));
        review.setDescription(description);
        review.setCreatedTime(LocalDateTime.now().minusWeeks(1));
        review.setUpdatedTime(LocalDateTime.now());
        review.setStatus(createApprovalStatus());
        reviewed.getReviews().add(review);
        return review;
    }

    @Override
    public ReviewRequest createReviewRequest(Project project) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setProject(project);
        reviewRequest.setId(getId());
        reviewRequest.setUpdatedTime(LocalDateTime.now());
        reviewRequest.setCreatedTime(LocalDateTime.now().minusMonths(2));
        reviewRequest.setDescription("new software version");
        reviewRequest.setSubmitter(createUser("user@user.com"));
        reviewRequest.setCommitId("0xdeadbeef");
        reviewRequest.setPublic(true);
        reviewRequest.setStatus(ReviewRequestStatus.MERGED);
        reviewRequest.setBranch("master");
        project.getReviewRequests().add(reviewRequest);
        return reviewRequest;
    }

    @Override
    public Comment createComment(String text, File file) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(createUser("user@example.com"));
        comment.setFile(file);
        comment.setId(getId());
        comment.setCreatedTime(LocalDateTime.now().minusWeeks(2));
        comment.setUpdatedTime(LocalDateTime.now().minusHours(1));
        file.getComments().add(comment);
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
        diff.setId(getId());
        diff.setReviewRequest(reviewRequest);
        diff.setCreatedTime(LocalDateTime.now().minusDays(2));
        diff.setUpdatedTime(LocalDateTime.now());
        reviewRequest.getDiffs().add(diff);
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
        file.setId(getId());
        file.setLinesInserted(1L);
        file.setLinesRemoved(2L);
        file.setStatus(FileStatus.MODIFIED);
        file.setNewFilename(filename);
        file.setDiff(diff);
        diff.getFiles().add(file);
        return file;
    }

    private long getId() {
        return ID_GENERATOR++;
    }

    private ApprovalStatus createApprovalStatus() {
        return new ApprovalStatus("label", "cool", 2, true, false);
    }
}
