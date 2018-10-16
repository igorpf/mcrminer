package com.mcrminer.export.perspectives.comment;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.export.impl.AbstractPerspectiveService;
import com.mcrminer.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentPerspectiveService extends AbstractPerspectiveService<Comment, CommentPerspective> {

    private final List<PerspectiveCreationStrategy <Comment, CommentPerspective>> strategies;

    @Autowired
    public CommentPerspectiveService(List<PerspectiveCreationStrategy <Comment, CommentPerspective>> strategies) {
        super(CommentPerspective.class);
        this.strategies = strategies;
    }

    @Override
    protected Iterable<PerspectiveCreationStrategy<Comment, CommentPerspective>> getCreationStrategies() {
        return strategies;
    }

    @Override
    public List<Comment> getRootEntitiesForProject(Long projectId) {
        return null;
    }
}
