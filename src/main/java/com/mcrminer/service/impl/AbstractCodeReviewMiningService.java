package com.mcrminer.service.impl;

import com.mcrminer.model.Comment;
import com.mcrminer.model.Diff;
import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;
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

    @Override
    public Project fetchProject(String host, String projectId, AuthenticationData authData) {
        Project project = getProject(host, projectId, authData);
        project = projectRepository.save(project);
        List<ReviewRequest> reviewRequests = getReviewRequestsForProject(project, authData);
        for (ReviewRequest reviewRequest : reviewRequests) {
            reviewRequest.setProject(project);
            populateReviewRequest(reviewRequest, authData);
            reviewRequestRepository.save(reviewRequest);
        }
        project.setReviewRequests(new HashSet<>(reviewRequests));
        return projectRepository.save(project);
    }

    private void populateReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData) {
        List<Diff> diffs = getDiffsForReviewRequest(reviewRequest, authData);
        diffs.forEach(this::saveDiff);

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

    protected abstract Project getProject(String host, String projectId, AuthenticationData authData);
    protected abstract List<ReviewRequest> getReviewRequestsForProject(Project project, AuthenticationData authData);
    protected abstract List<Diff> getDiffsForReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData);
}
