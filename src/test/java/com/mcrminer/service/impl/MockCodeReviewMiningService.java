package com.mcrminer.service.impl;

import com.mcrminer.model.*;
import com.mcrminer.service.AuthenticationData;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("mockCodeReviewMiningService")
public class MockCodeReviewMiningService extends AbstractCodeReviewMiningService {

    @Override
    protected Project getProject(String host, String projectId, AuthenticationData authData) {
        Project project = new Project();
        project.setUrlPath(String.format("%s/%s", host, projectId));
        project.setName("Mock project");
        return project;
    }

    @Override
    protected List<ReviewRequest> getReviewRequestsForProject(Project project, AuthenticationData authData) {
        ReviewRequest reviewRequest1 = new ReviewRequest();
        ReviewRequest reviewRequest2 = new ReviewRequest();
        return Arrays.asList(reviewRequest1, reviewRequest2);
    }

    @Override
    protected List<Diff> getDiffsForReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData) {
        User user = new User();
        user.setEmail("user@example.com");
        Comment comment1 = new Comment();
        comment1.setText("Some comment");
        comment1.setAuthor(user);
        Comment comment2 = new Comment();
        comment2.setText("Some other comment");
        comment2.setAuthor(user);
        File file1 = new File();
        file1.setComments(Arrays.asList(comment1, comment2));
        file1.setNewFilename("filename");
        Diff diff = new Diff();
        diff.setFiles(Collections.singletonList(file1));
        return Collections.singletonList(diff);
    }

    @Override
    protected List<Review> getReviewsForReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData) {
        User user = new User();
        user.setEmail("someuser@email.com");
        ApprovalStatus status = new ApprovalStatus();
        status.setApproval(true);
        status.setValue(2);
        status.setLabel("+2");
        Review review = new Review();
        review.setStatus(status);
        review.setDescription("very good");
        review.setAuthor(user);
        Review review2 = new Review();
        review2.setStatus(status);
        review2.setDescription("awesome");
        review2.setAuthor(user);
        return Arrays.asList(review, review2);
    }
}
