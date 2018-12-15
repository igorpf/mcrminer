package com.mcrminer.service.export.perspectives.comment;

import com.mcrminer.persistence.model.Comment;
import com.mcrminer.service.export.PerspectiveCreationStrategy;

public class MockCommentPerspectiveCreationStrategy implements PerspectiveCreationStrategy<Comment, CommentPerspective> {
    @Override
    public void fillPerspective(Comment comment, CommentPerspective perspective) {
        perspective.setComment(comment.getText());
        perspective.setFilename(comment.getFile().getNewFilename());
        perspective.setId(comment.getId());
    }
}
