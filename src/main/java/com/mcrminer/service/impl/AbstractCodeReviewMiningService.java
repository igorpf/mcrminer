package com.mcrminer.service.impl;

import com.mcrminer.exceptions.ProjectAlreadyImportedException;
import com.mcrminer.model.*;
import com.mcrminer.repository.*;
import com.mcrminer.service.AuthenticationData;
import com.mcrminer.service.CodeReviewMiningService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
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
    private static final int PAGE_SIZE = 5;

    @Override
    public Project fetchProject(String projectId, AuthenticationData authData) {
        Project project = getProject(projectId, authData);
        if (projectRepository.existsByNameAndUrlPath(project.getName(), project.getUrlPath()))
            throw new ProjectAlreadyImportedException("This project already exists");
        project = projectRepository.save(project);
        boolean hasMoreData = true;
        int page = 0;
        while(hasMoreData) {
            Pageable pageRequest = PageRequest.of(page++, PAGE_SIZE);
            List<ReviewRequest> reviewRequests = getReviewRequestsForProject(project, pageRequest, authData);
            for (ReviewRequest reviewRequest : reviewRequests) {
                reviewRequest.setProject(project);
                saveReviewRequest(reviewRequest, authData);
            }
            hasMoreData = reviewRequests.size() == pageRequest.getPageSize();
        }
        return project;
    }

    private void saveReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData) {
        if (reviewRequest.getSubmitter() != null)
            userRepository.save(reviewRequest.getSubmitter());
        reviewRequestRepository.save(reviewRequest);
        int page = 0;
        boolean hasMoreData = true;
        while(hasMoreData) {
            Pageable pageRequest = PageRequest.of(page++, PAGE_SIZE);
            List<Diff> diffs = getDiffsForReviewRequest(reviewRequest, pageRequest, authData);
            for (Diff diff : diffs) {
                diff.setReviewRequest(reviewRequest);
                saveDiff(diff);
            }
            hasMoreData = diffs.size() == pageRequest.getPageSize();
        }
        hasMoreData = true;
        page = 0;
        while(hasMoreData) {
            Pageable pageRequest = PageRequest.of(page++, PAGE_SIZE);
            List<Review> reviews = getReviewsForReviewRequest(reviewRequest, pageRequest, authData);
            for (Review review : reviews) {
                review.setReviewed(reviewRequest);
                saveReview(review);
            }
            hasMoreData = reviews.size() == pageRequest.getPageSize();
        }
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
    protected abstract List<ReviewRequest> getReviewRequestsForProject(Project project, Pageable pageRequest, AuthenticationData authData);
    protected abstract List<Diff> getDiffsForReviewRequest(ReviewRequest reviewRequest, Pageable pageRequest, AuthenticationData authData);
    protected abstract List<Review> getReviewsForReviewRequest(ReviewRequest reviewRequest, Pageable pageRequest, AuthenticationData authData);
}
