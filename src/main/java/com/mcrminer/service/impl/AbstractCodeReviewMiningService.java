package com.mcrminer.service.impl;

import com.mcrminer.exceptions.ProjectAlreadyImportedException;
import com.mcrminer.model.*;
import com.mcrminer.repository.*;
import com.mcrminer.service.AuthenticationData;
import com.mcrminer.service.CodeReviewMiningService;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractCodeReviewMiningService implements CodeReviewMiningService {

    private ProjectRepository projectRepository;
    private ReviewRequestRepository reviewRequestRepository;
    private DiffRepository diffRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private ReviewRepository reviewRepository;
    private ApprovalStatusRepository approvalStatusRepository;

    @Override
    public Project fetchProject(String projectId, AuthenticationData authData) {
        Project project = getProject(projectId, authData);
        if (projectRepository.existsByNameAndUrlPath(project.getName(), project.getUrlPath()))
            throw new ProjectAlreadyImportedException("This project already exists");
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
        Collection<File> files = diff.getFiles();
        diff.setFiles(null);
        diffRepository.save(diff);
        files.forEach(file -> {
            Collection<Comment> comments = file.getComments();
            file.setComments(null);
            file.setDiff(diff);
            fileRepository.save(file);
            if (comments != null) {
                saveComments(comments, file);
                file.setComments(comments);
            }
        });
        diff.setFiles(files);
        diffRepository.save(diff);
    }

    private void saveComments(Iterable<Comment> comments, File file) {
        comments.forEach(comment -> {
            if (!commentAuthorIsAlreadySaved(comment))
                userRepository.save(comment.getAuthor());
            else if (comment.getAuthor() != null)
                comment.setAuthor(userRepository.findByEmail(comment.getAuthor().getEmail()));
            comment.setFile(file);
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
