package com.mcrminer.export.perspectives.comment;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.export.impl.AbstractPerspectiveService;
import com.mcrminer.model.Comment;
import com.mcrminer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentPerspectiveService extends AbstractPerspectiveService<Comment, CommentPerspective> {

    private final List<PerspectiveCreationStrategy <Comment, CommentPerspective>> strategies;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentPerspectiveService(List<PerspectiveCreationStrategy <Comment, CommentPerspective>> strategies,
                                     CommentRepository commentRepository) {
        super(CommentPerspective.class);
        this.strategies = strategies;
        this.commentRepository = commentRepository;
    }

    @Override
    protected Iterable<PerspectiveCreationStrategy<Comment, CommentPerspective>> getCreationStrategies() {
        return strategies;
    }

    @Override
    public List<Comment> getRootEntitiesForProject(Long projectId) {
        return commentRepository.findAllByFileDiffReviewRequestProjectId(projectId);
    }
}
