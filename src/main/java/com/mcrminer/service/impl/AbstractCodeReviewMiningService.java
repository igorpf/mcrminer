package com.mcrminer.service.impl;

import com.mcrminer.model.*;
import com.mcrminer.repository.*;
import com.mcrminer.service.AuthenticationData;
import com.mcrminer.service.CodeReviewMiningService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

public abstract class AbstractCodeReviewMiningService implements CodeReviewMiningService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ReviewRequestRepository reviewRequestRepository;
    @Autowired
    private DiffRepository diffRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ApprovalStatusRepository approvalStatusRepository;

    @Override
    public Project fetchProject(String projectId, AuthenticationData authData) {
        Project project = getProject(projectId, authData);
        project = projectRepository.save(project);
        List<ReviewRequest> reviewRequests = getReviewRequestsForProject(project, authData);
        reviewRequests.forEach(reviewRequest -> saveReviewRequest(reviewRequest, authData));
        project.setReviewRequests(new HashSet<>(reviewRequests));
        return projectRepository.save(project);
    }

    private void saveReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData) {
        List<Diff> diffs = getDiffsForReviewRequest(reviewRequest, authData);
        diffs.forEach(this::saveDiff);
        reviewRequest.setDiffs(new HashSet<>(diffs));

        List<Review> reviews = getReviewsForReviewRequest(reviewRequest, authData);
        reviews.forEach(this::saveReview);
        reviewRequest.setReviews(new HashSet<>(reviews));
        reviewRequestRepository.save(reviewRequest);
    }

    private void saveDiff(Diff diff) {
        diff.getFiles().forEach(file -> {
            saveComments(file.getComments());
            fileRepository.save(file);
        });
        diffRepository.save(diff);
    }

    private void saveComments(Iterable<Comment> comments) {
        comments.forEach(comment -> {
            userRepository.save(comment.getAuthor());
            commentRepository.save(comment);
        });
    }

    private void saveReview(Review review) {
        userRepository.save(review.getAuthor());
        approvalStatusRepository.save(review.getStatus());
        reviewRepository.save(review);
    }

    protected abstract Project getProject(String projectId, AuthenticationData authData);
    protected abstract List<ReviewRequest> getReviewRequestsForProject(Project project, AuthenticationData authData);
    protected abstract List<Diff> getDiffsForReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData);
    protected abstract List<Review> getReviewsForReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData);
}
