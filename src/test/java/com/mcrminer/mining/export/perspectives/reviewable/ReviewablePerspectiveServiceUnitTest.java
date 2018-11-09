package com.mcrminer.mining.export.perspectives.reviewable;

import com.mcrminer.mining.export.PerspectiveCreationStrategy;
import com.mcrminer.persistence.model.Diff;
import com.mcrminer.persistence.model.Project;
import com.mcrminer.persistence.model.ReviewRequest;
import com.mcrminer.persistence.model.Reviewable;
import com.mcrminer.persistence.repository.FileRepository;
import com.mcrminer.persistence.repository.factory.DomainObjectsFactory;
import com.mcrminer.persistence.repository.factory.impl.TransientObjectsFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewablePerspectiveServiceUnitTest {

    private DomainObjectsFactory domainObjectsFactory = new TransientObjectsFactory();
    private ReviewablePerspectiveService reviewablePerspectiveService;
    private FileRepository fileRepository = mock(FileRepository.class);
    private List<PerspectiveCreationStrategy<Reviewable, ReviewablePerspective>> strategies = new ArrayList<>();
    private Project project;
    private ReviewRequest reviewRequest1;
    private Diff diff1;

    @Before
    public void setUp() {
        strategies.add(new ReviewRequestReviewablePerspectiveCreationStrategy(fileRepository));
        strategies.add(new DiffReviewablePerspectiveCreationStrategy(fileRepository));
        reviewablePerspectiveService = new ReviewablePerspectiveService(strategies, null, null);
        project = domainObjectsFactory.createProject("www.example.com", "Sample project");
        reviewRequest1 = domainObjectsFactory.createReviewRequest(project);
        diff1 = domainObjectsFactory.createDiff(reviewRequest1);
        when(fileRepository.findAllByDiffId(any())).thenReturn(Collections.EMPTY_LIST);
        when(fileRepository.findAllByDiffReviewRequestId(any())).thenReturn(Collections.EMPTY_LIST);
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