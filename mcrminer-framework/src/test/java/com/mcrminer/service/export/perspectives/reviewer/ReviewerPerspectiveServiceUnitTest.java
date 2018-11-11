package com.mcrminer.service.export.perspectives.reviewer;

import com.mcrminer.persistence.model.*;
import com.mcrminer.persistence.repository.DiffRepository;
import com.mcrminer.persistence.repository.FileRepository;
import com.mcrminer.persistence.repository.ReviewRepository;
import com.mcrminer.persistence.repository.ReviewRequestRepository;
import com.mcrminer.persistence.repository.factory.DomainObjectsFactory;
import com.mcrminer.persistence.repository.factory.impl.TransientObjectsFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ReviewerPerspectiveServiceUnitTest {

    private DomainObjectsFactory domainObjectsFactory = new TransientObjectsFactory();
    private ReviewerPerspectiveService reviewerPerspectiveService;
    private Diff diff;
    private ReviewRequest reviewRequest;
    private User author;
    private ReviewRequestRepository reviewRequestRepository = mock(ReviewRequestRepository.class);
    private FileRepository fileRepository = mock(FileRepository.class);
    private ReviewRepository reviewRepository = mock(ReviewRepository.class);
    private DiffRepository diffRepository = mock(DiffRepository.class);

    @Before
    public void setUp() throws Exception {
        reviewerPerspectiveService = new ReviewerPerspectiveService(Arrays.asList(
                new DiffReviewerPerspectiveCreationStrategy(reviewRequestRepository, fileRepository),
                new ReviewerAssociationsPerspectiveCreationStrategy(),
                new ReviewRequestReviewerPerspectiveCreationStrategy(diffRepository, fileRepository, reviewRepository)
        ),null);
        Project project = domainObjectsFactory.createProject("https://localhost", "Localhost project");
        reviewRequest = domainObjectsFactory.createReviewRequest(project);
        diff = domainObjectsFactory.createDiff(reviewRequest);
        reviewRequest.setDiffs(Collections.singleton(diff));
        author = domainObjectsFactory.createUser("example@example.com");
        author.setReviewRequests(Collections.singleton(reviewRequest));
    }

    @Test
    public void testDiffReviewerPerspective() {
        Review review1 = domainObjectsFactory.createReview("cool", diff);
        review1.setAuthor(author);
        diff.setReviews(Collections.singleton(review1));
        assertNotNull(reviewerPerspectiveService.createPerspective(author));
    }

    @Test
    public void testReviewRequestReviewerPerspective() {
        Review review1 = domainObjectsFactory.createReview("cool", diff);
        review1.setAuthor(author);
        reviewRequest.setReviews(Collections.singleton(review1));
        assertNotNull(reviewerPerspectiveService.createPerspective(author));
    }
}