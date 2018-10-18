package com.mcrminer.export.perspectives.reviewable;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.export.impl.AbstractPerspectiveService;
import com.mcrminer.model.Reviewable;
import com.mcrminer.repository.DiffRepository;
import com.mcrminer.repository.ReviewRequestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewablePerspectiveService extends AbstractPerspectiveService <Reviewable, ReviewablePerspective> {

    private final List<PerspectiveCreationStrategy<Reviewable, ReviewablePerspective>> strategies;
    private final DiffRepository diffRepository;
    private final ReviewRequestRepository reviewRequestRepository;

    public ReviewablePerspectiveService(List<PerspectiveCreationStrategy<Reviewable, ReviewablePerspective>> strategies,
                                        DiffRepository diffRepository,
                                        ReviewRequestRepository reviewRequestRepository) {
        super(ReviewablePerspective.class);
        this.strategies = strategies;
        this.diffRepository = diffRepository;
        this.reviewRequestRepository = reviewRequestRepository;
    }

    @Override
    protected Iterable<PerspectiveCreationStrategy<Reviewable, ReviewablePerspective>> getCreationStrategies() {
        return strategies;
    }

    @Override
    public List<Reviewable> getRootEntitiesForProject(Long projectId) {
        List<Reviewable> reviewables = new ArrayList<>();
        reviewables.addAll(diffRepository.findAllByReviewRequestProjectId(projectId));
        reviewables.addAll(reviewRequestRepository.findAllByProjectId(projectId));
        return reviewables;
    }
}
