package com.mcrminer.statistics.impl.project;

import com.mcrminer.model.*;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import com.mcrminer.repository.factory.impl.TransientObjectsFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProjectStatisticsServiceUnitTest {

    private DomainObjectsFactory domainObjectsFactory = new TransientObjectsFactory();
    private ProjectStatisticsService projectStatisticsService;
    private ProjectStatisticsCalculationStrategy strategy = new ProjectStatisticsCalculationStrategy();
    private Project project;
    private ReviewRequest reviewRequest1, reviewRequest2;
    private Diff diff1, diff2;
    private File file1, file2, file3, file4;
    private Comment comment1, comment2, comment3, comment4, comment5, comment6;

    @Before
    public void setUp() throws Exception {
        projectStatisticsService = new ProjectStatisticsService(Collections.singletonList(strategy));
        project = domainObjectsFactory.createProject("https://localhost:9002", "Test project");
        reviewRequest1 = domainObjectsFactory.createReviewRequest(project);
        diff1 = domainObjectsFactory.createDiff(reviewRequest1);
        file1 = domainObjectsFactory.createFile("file1", diff1);
        file2 = domainObjectsFactory.createFile("file2", diff1);
        comment1 = domainObjectsFactory.createComment("nice", file1);
        comment2 = domainObjectsFactory.createComment("cool", file1);

        reviewRequest2 = domainObjectsFactory.createReviewRequest(project);
        diff2 = domainObjectsFactory.createDiff(reviewRequest2);
        file3 = domainObjectsFactory.createFile("file1", diff2);
        file4 = domainObjectsFactory.createFile("file2", diff2);
        comment3 = domainObjectsFactory.createComment("nice", file3);
        comment4 = domainObjectsFactory.createComment("cool", file3);
        comment5 = domainObjectsFactory.createComment("nice", file3);
        comment6 = domainObjectsFactory.createComment("cool", file4);

    }

    @Test
    public void testGetProjectStatistics() {
        ProjectStatistics statistics = projectStatisticsService.calculateStatistics(project);
        assertThat(statistics.getComments(), equalTo(6L));
        assertThat(statistics.getAverageCommentSize(), equalTo(4.0));
        assertThat(statistics.getReviews(), equalTo(0L));
        assertThat(statistics.getCommentedFilesPercentage(), equalTo(0.75));
    }
}