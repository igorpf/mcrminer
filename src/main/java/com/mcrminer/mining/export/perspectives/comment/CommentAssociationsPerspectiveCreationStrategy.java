package com.mcrminer.mining.export.perspectives.comment;

import com.mcrminer.mining.export.PerspectiveCreationStrategy;
import com.mcrminer.persistence.model.Comment;
import com.mcrminer.persistence.model.Project;
import com.mcrminer.persistence.model.ReviewRequest;
import com.mcrminer.persistence.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentAssociationsPerspectiveCreationStrategy implements PerspectiveCreationStrategy <Comment, CommentPerspective> {

    @Transactional
    @Override
    public void fillPerspective(Comment comment, CommentPerspective perspective) {
        perspective.setComment(comment.getText());
        perspective.setFilename(comment.getFile().getNewFilename());
        perspective.setId(comment.getId());
        fillAuthor(perspective, comment.getAuthor());
        fillReviewRequestSubmitter(perspective, comment.getFile().getDiff().getReviewRequest().getSubmitter());
        fillReviewRequest(comment, perspective);
        fillProject(comment, perspective);
    }

    private void fillAuthor(CommentPerspective perspective, User user) {
        if (user != null) {
            perspective.setAuthorEmail(user.getEmail());
            perspective.setAuthorFullname(user.getFullname());
            perspective.setAuthorUsername(user.getUsername());
        }
    }

    private void fillReviewRequestSubmitter(CommentPerspective perspective, User user) {
        if (user != null) {
            perspective.setReviewRequestSubmitterEmail(user.getEmail());
            perspective.setReviewRequestSubmitterFullname(user.getFullname());
            perspective.setReviewRequestSubmitterUsername(user.getUsername());
        }
    }

    private void fillReviewRequest(Comment comment, CommentPerspective perspective) {
        ReviewRequest reviewRequest = comment.getFile().getDiff().getReviewRequest();
        perspective.setReviewRequestId(reviewRequest.getId());
        perspective.setBranch(reviewRequest.getBranch());
        perspective.setCommitId(reviewRequest.getCommitId());
        perspective.setPublic(reviewRequest.isPublic());
        perspective.setStatus(reviewRequest.getStatus());
        perspective.setDescription(reviewRequest.getDescription());
    }

    private void fillProject(Comment comment, CommentPerspective perspective) {
        Project project = comment.getFile().getDiff().getReviewRequest().getProject();
        perspective.setProjectId(project.getId());
        perspective.setCodeReviewToolId(project.getCodeReviewToolId());
        perspective.setUrlPath(project.getUrlPath());
        perspective.setName(project.getName());
    }
}
