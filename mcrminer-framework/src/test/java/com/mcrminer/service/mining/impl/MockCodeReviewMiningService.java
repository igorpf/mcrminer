package com.mcrminer.service.mining.impl;

import com.mcrminer.persistence.model.*;
import com.mcrminer.persistence.repository.*;
import com.mcrminer.service.mining.AuthenticationData;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("mockCodeReviewMiningService")
public class MockCodeReviewMiningService extends AbstractCodeReviewMiningService {

    public MockCodeReviewMiningService(ProjectRepository projectRepository, ReviewRequestRepository reviewRequestRepository, DiffRepository diffRepository, CommentRepository commentRepository, UserRepository userRepository, FileRepository fileRepository, ReviewRepository reviewRepository, ApprovalStatusRepository approvalStatusRepository) {
        super(projectRepository, reviewRequestRepository, diffRepository, commentRepository, userRepository, fileRepository, reviewRepository, approvalStatusRepository);
    }

    @Override
    protected Project getProject(String projectId, AuthenticationData authData) {
        Project project = new Project();
        project.setUrlPath(String.format("%s/%s", authData.getHost(), projectId));
        project.setName("Mock project");
        return project;
    }

    @Override
    protected List<ReviewRequest> getReviewRequestsForProject(Project project, Pageable pageable, AuthenticationData authData) {
        ReviewRequest reviewRequest1 = new ReviewRequest();
        ReviewRequest reviewRequest2 = new ReviewRequest();
        return Arrays.asList(reviewRequest1, reviewRequest2);
    }

    @Override
    protected List<Diff> getDiffsForReviewRequest(ReviewRequest reviewRequest, Pageable pageRequest, AuthenticationData authData) {
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
    protected List<Review> getReviewsForReviewRequest(ReviewRequest reviewRequest, Pageable pageRequest, AuthenticationData authData) {
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

        ApprovalStatus status2 = new ApprovalStatus();
        status2.setApproval(true);
        status2.setValue(2);
        status2.setLabel("+2");
        Review review2 = new Review();
        review2.setStatus(status2);
        review2.setDescription("awesome");
        review2.setAuthor(user);

        ApprovalStatus status3 = new ApprovalStatus();
        status3.setApproval(true);
        status3.setValue(1);
        status3.setLabel("+1");
        Review review3 = new Review();
        review3.setStatus(status2);
        review3.setDescription("so so");
        review3.setAuthor(user);

        return Arrays.asList(review, review2, review3);
    }

    @Override
    public String getToolName() {
        return "Mock tool";
    }
}
