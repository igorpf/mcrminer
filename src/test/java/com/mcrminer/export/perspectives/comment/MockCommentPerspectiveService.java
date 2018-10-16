package com.mcrminer.export.perspectives.comment;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.export.impl.AbstractPerspectiveService;
import com.mcrminer.model.Comment;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import com.mcrminer.repository.factory.impl.TransientObjectsFactory;

import java.util.Arrays;
import java.util.List;

public class MockCommentPerspectiveService extends AbstractPerspectiveService<Comment, CommentPerspective> {

    private final Iterable<PerspectiveCreationStrategy<Comment, CommentPerspective>> strategies;
    private final DomainObjectsFactory domainObjectsFactory;

    public MockCommentPerspectiveService(Iterable<PerspectiveCreationStrategy<Comment, CommentPerspective>> strategies) {
        super(CommentPerspective.class);
        this.strategies = strategies;
        domainObjectsFactory = new TransientObjectsFactory();
    }

    @Override
    protected Iterable<PerspectiveCreationStrategy<Comment, CommentPerspective>> getCreationStrategies() {
        return strategies;
    }

    @Override
    public List<Comment> getRootEntitiesForProject(Long projectId) {
        return Arrays.asList(
                domainObjectsFactory.createComment("hello"),
                domainObjectsFactory.createComment("hi"),
                domainObjectsFactory.createComment("some comment"),
                domainObjectsFactory.createComment("test comment")
        );
    }
}
