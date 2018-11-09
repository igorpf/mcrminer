package com.mcrminer.mining.export.perspectives.reviewer;

import com.mcrminer.mining.export.PerspectiveCreationStrategy;
import com.mcrminer.mining.export.impl.AbstractPerspectiveService;
import com.mcrminer.persistence.model.User;
import com.mcrminer.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerPerspectiveService extends AbstractPerspectiveService<User, ReviewerPerspective> {

    private final List<PerspectiveCreationStrategy<User, ReviewerPerspective>> strategies;
    private final UserRepository userRepository;

    public ReviewerPerspectiveService(List<PerspectiveCreationStrategy<User, ReviewerPerspective>> strategies,
                                      UserRepository userRepository) {
        super(ReviewerPerspective.class);
        this.strategies = strategies;
        this.userRepository = userRepository;
    }

    @Override
    protected Iterable<PerspectiveCreationStrategy<User, ReviewerPerspective>> getCreationStrategies() {
        return strategies;
    }

    @Override
    public List<User> getRootEntitiesForProject(Long projectId) {
        return userRepository.findAllByReviewRequestsProjectId(projectId);
    }
}
