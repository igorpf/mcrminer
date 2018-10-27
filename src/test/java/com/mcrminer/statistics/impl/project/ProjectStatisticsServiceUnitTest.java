package com.mcrminer.statistics.impl.project;

import com.mcrminer.model.*;
import com.mcrminer.repository.*;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import com.mcrminer.repository.factory.impl.TransientObjectsFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectStatisticsServiceUnitTest {

    private DomainObjectsFactory domainObjectsFactory = new TransientObjectsFactory();
    private ProjectStatisticsService projectStatisticsService;
    private ProjectRepository projectRepository = mock(ProjectRepository.class);
    private FileRepository fileRepository = mock(FileRepository.class);
    private CommentRepository commentRepository = mock(CommentRepository.class);
    private ReviewRequestRepository reviewRequestRepository = mock(ReviewRequestRepository.class);
    private DiffRepository diffRepository = mock(DiffRepository.class);
    private ProjectStatisticsCalculationStrategy projectStatisticsCalculationStrategy = new ProjectStatisticsCalculationStrategy(projectRepository, commentRepository);
    private ProjectFileStatisticsCalculationStrategy projectFileStatisticsCalculationStrategy = new ProjectFileStatisticsCalculationStrategy(fileRepository);
    private ProjectReviewStatisticsCalculationStrategy projectReviewStatisticsCalculationStrategy = new ProjectReviewStatisticsCalculationStrategy(diffRepository, reviewRequestRepository);
    private Project project;
    private ReviewRequest reviewRequest1, reviewRequest2;
    private Diff diff1, diff2;
    private File file1, file2, file3, file4;
    private Comment comment1, comment2, comment3, comment4, comment5, comment6;

    @Before
    public void setUp() throws Exception {
        projectStatisticsService = new ProjectStatisticsService(Arrays.asList(
                projectFileStatisticsCalculationStrategy,
                projectStatisticsCalculationStrategy)
        );
        initializeObjects();
        when(projectRepository.getById(any())).thenReturn(project);
        when(reviewRequestRepository.findAllByProjectId(any())).thenReturn(Arrays.asList(reviewRequest1, reviewRequest2));
        when(diffRepository.findAllByReviewRequestProjectId(any())).thenReturn(Arrays.asList(diff1, diff2));
        when(fileRepository.findAllByDiffReviewRequestProjectId(any())).thenReturn(Arrays.asList(file1, file2, file3, file4));
        when(commentRepository.findAllByFileDiffReviewRequestProjectId(any())).thenReturn(Arrays.asList(comment1, comment2, comment3, comment4, comment5, comment6));
    }

    @Test
    public void testGetProjectStatistics() {
        ProjectStatistics statistics = projectStatisticsService.calculateStatistics(project);
        assertThat(statistics.getComments(), equalTo(6L));
        assertThat(statistics.getAverageCommentSize(), equalTo(4.0));
        assertThat(statistics.getReviews(), equalTo(0L));
        assertThat(statistics.getCommentedFilesPercentage(), equalTo(0.75));
        assertThat(statistics.getChangedFiles(), equalTo(4L));
        assertThat(statistics.getReviewers(), equalTo(0L));
        assertThat(statistics.getCommentsPerReview(), equalTo(0.0));
    }

    private void initializeObjects() {
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
}