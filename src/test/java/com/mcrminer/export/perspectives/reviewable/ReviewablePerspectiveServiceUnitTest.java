package com.mcrminer.export.perspectives.reviewable;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.Diff;
import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.model.Reviewable;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import com.mcrminer.repository.factory.impl.TransientObjectsFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ReviewablePerspectiveServiceUnitTest {

    private DomainObjectsFactory domainObjectsFactory = new TransientObjectsFactory();
    private ReviewablePerspectiveService reviewablePerspectiveService;
    private List<PerspectiveCreationStrategy<Reviewable, ReviewablePerspective>> strategies = new ArrayList<>();
    private Project project;
    private ReviewRequest reviewRequest1;
    private Diff diff1;

    @Before
    public void setUp() {
        strategies.add(new ReviewRequestReviewablePerspectiveCreationStrategy());
        strategies.add(new DiffReviewablePerspectiveCreationStrategy());
        reviewablePerspectiveService = new ReviewablePerspectiveService(strategies, null, null);
        project = domainObjectsFactory.createProject("www.example.com", "Sample project");
        reviewRequest1 = domainObjectsFactory.createReviewRequest(project);
        diff1 = domainObjectsFactory.createDiff(reviewRequest1);
    }

    @Test
    public void testReviewRequestReviewablePerspective() {
        assertNotNull(reviewablePerspectiveService.createPerspective(reviewRequest1));
    }

    @Test
    public void testDiffReviewablePerspective() {
        assertNotNull(reviewablePerspectiveService.createPerspective(diff1));
    }
}