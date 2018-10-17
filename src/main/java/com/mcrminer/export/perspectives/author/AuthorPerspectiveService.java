package com.mcrminer.export.perspectives.author;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.export.impl.AbstractPerspectiveService;
import com.mcrminer.model.User;
import com.mcrminer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorPerspectiveService extends AbstractPerspectiveService<User, AuthorPerspective> {

    private final List<PerspectiveCreationStrategy<User, AuthorPerspective>> strategies;
    private final UserRepository userRepository;

    public AuthorPerspectiveService(List<PerspectiveCreationStrategy<User, AuthorPerspective>> strategies,
                                    UserRepository userRepository) {
        super(AuthorPerspective.class);
        this.strategies = strategies;
        this.userRepository = userRepository;
    }

    @Override
    protected Iterable<PerspectiveCreationStrategy<User, AuthorPerspective>> getCreationStrategies() {
        return strategies;
    }

    @Override
    public List<User> getRootEntitiesForProject(Long projectId) {
        return userRepository.findAllByReviewRequestsProjectId(projectId);
    }
}
