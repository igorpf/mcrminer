package com.mcrminer.export.perspectives.comment;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.Comment;

public class MockCommentPerspectiveCreationStrategy implements PerspectiveCreationStrategy<Comment, CommentPerspective> {
    @Override
    public void fillPerspective(Comment comment, CommentPerspective perspective) {
        perspective.setComment(comment.getText());
        perspective.setFilename(comment.getFile().getNewFilename());
        perspective.setId(comment.getId());
    }
}
