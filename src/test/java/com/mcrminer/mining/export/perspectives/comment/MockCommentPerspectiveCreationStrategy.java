package com.mcrminer.mining.export.perspectives.comment;

import com.mcrminer.mining.export.PerspectiveCreationStrategy;
import com.mcrminer.persistence.model.Comment;

public class MockCommentPerspectiveCreationStrategy implements PerspectiveCreationStrategy<Comment, CommentPerspective> {
    @Override
    public void fillPerspective(Comment comment, CommentPerspective perspective) {
        perspective.setComment(comment.getText());
        perspective.setFilename(comment.getFile().getNewFilename());
        perspective.setId(comment.getId());
    }
}
