package com.mcrminer.export.perspectives.comment;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.export.impl.AbstractPerspectiveService;
import com.mcrminer.model.Comment;

public class MockCommentPerspectiveService extends AbstractPerspectiveService<Comment, CommentPerspective> {

    private final Iterable<PerspectiveCreationStrategy<Comment, CommentPerspective>> strategies;

    public MockCommentPerspectiveService(Iterable<PerspectiveCreationStrategy<Comment, CommentPerspective>> strategies) {
        super(CommentPerspective.class);
        this.strategies = strategies;
    }

    @Override
    protected Iterable<PerspectiveCreationStrategy<Comment, CommentPerspective>> getCreationStrategies() {
        return strategies;
    }
}
