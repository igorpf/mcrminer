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
        for (ReviewRequest reviewRequest : reviewRequests) {
            reviewRequest.setProject(project);
            saveReviewRequest(reviewRequest, authData);
        }
        project.setReviewRequests(new HashSet<>(reviewRequests));
        return projectRepository.save(project);
    }

    private void saveReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData) {
        if (reviewRequest.getSubmitter() != null)
            userRepository.save(reviewRequest.getSubmitter());
        reviewRequestRepository.save(reviewRequest);
        List<Diff> diffs = getDiffsForReviewRequest(reviewRequest, authData);
        for (Diff diff : diffs) {
            diff.setReviewRequest(reviewRequest);
            saveDiff(diff);
        }
        reviewRequest.setDiffs(new HashSet<>(diffs));

        List<Review> reviews = getReviewsForReviewRequest(reviewRequest, authData);
        for (Review review : reviews) {
            review.setReviewed(reviewRequest);
            saveReview(review);
        }
        reviewRequest.setReviews(new HashSet<>(reviews));

        reviewRequestRepository.save(reviewRequest);
    }

    private void saveDiff(Diff diff) {
        diff.getFiles().forEach(file -> {
            if (file.getComments() != null)
                saveComments(file.getComments());
            fileRepository.save(file);
        });
        diffRepository.save(diff);
    }

    private void saveComments(Iterable<Comment> comments) {
        comments.forEach(comment -> {
            if (!commentAuthorIsAlreadySaved(comment))
                userRepository.save(comment.getAuthor());
            else if (comment.getAuthor() != null)
                comment.setAuthor(userRepository.findByEmail(comment.getAuthor().getEmail()));
            commentRepository.save(comment);
        });
    }

    private boolean commentAuthorIsAlreadySaved(Comment comment) {
        return comment.getAuthor() != null && !userRepository.existsByEmail(comment.getAuthor().getEmail());
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
