package com.mcrminer.export.perspectives.comment;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.export.impl.AbstractPerspectiveService;
import com.mcrminer.model.Comment;

public class CommentPerspectiveService extends AbstractPerspectiveService<Comment, CommentPerspective> {
    public CommentPerspectiveService() {
        super(CommentPerspective.class);
    }

    @Override
    protected Iterable<PerspectiveCreationStrategy<Comment, CommentPerspective>> getCreationStrategies() {
        return null;
    }
}
