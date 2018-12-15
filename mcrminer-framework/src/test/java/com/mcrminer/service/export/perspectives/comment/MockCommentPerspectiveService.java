package com.mcrminer.service.export.perspectives.comment;

import com.mcrminer.persistence.model.Comment;
import com.mcrminer.persistence.repository.factory.DomainObjectsFactory;
import com.mcrminer.persistence.repository.factory.impl.TransientObjectsFactory;
import com.mcrminer.service.export.PerspectiveCreationStrategy;
import com.mcrminer.service.export.impl.AbstractPerspectiveService;

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
