package com.mcrminer.service.export.perspectives.author;

import com.mcrminer.persistence.model.User;
import com.mcrminer.persistence.repository.UserRepository;
import com.mcrminer.service.export.PerspectiveCreationStrategy;
import com.mcrminer.service.export.impl.AbstractPerspectiveService;
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
