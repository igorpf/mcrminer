package com.mcrminer.export.perspectives.reviewer;

import com.mcrminer.model.*;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import com.mcrminer.repository.factory.impl.TransientObjectsFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class ReviewerPerspectiveServiceUnitTest {

    private DomainObjectsFactory domainObjectsFactory = new TransientObjectsFactory();
    private ReviewerPerspectiveService reviewerPerspectiveService;
    private Diff diff;
    private ReviewRequest reviewRequest;
    private User author;

    @Before
    public void setUp() throws Exception {
        reviewerPerspectiveService = new ReviewerPerspectiveService(Arrays.asList(
                new DiffReviewerPerspectiveCreationStrategy(),
                new ReviewerAssociationsPerspectiveCreationStrategy(),
                new ReviewRequestReviewerPerspectiveCreationStrategy()
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