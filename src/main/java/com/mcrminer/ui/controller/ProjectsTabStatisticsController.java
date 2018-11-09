package com.mcrminer.ui.controller;

import com.mcrminer.mining.statistics.StatisticsService;
import com.mcrminer.mining.statistics.impl.project.ProjectStatistics;
import com.mcrminer.persistence.model.Project;
import com.mcrminer.ui.tasks.CalculateProjectStatisticsTask;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ProjectsTabStatisticsController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectsTabStatisticsController.class);

    private final StatisticsService<Project, ProjectStatistics> projectStatisticsService;

    @FXML
    private Label changedLinesOfCode, reviewers, reviews, comments, changedFiles, commentsPerReview, averageReviewTimeInMinutes,
                  commentedFilesPercentage, averageCommentSize;

    @Autowired
    public ProjectsTabStatisticsController(StatisticsService<Project, ProjectStatistics> projectStatisticsService) {
        this.projectStatisticsService = projectStatisticsService;
    }

    private Project selectedProject;

    void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
        getProjectStatistics(selectedProject);
    }

    private void getProjectStatistics(Project project) {
        CalculateProjectStatisticsTask task = new CalculateProjectStatisticsTask(projectStatisticsService, project);

        final Instant now = Instant.now();

        task.setOnSucceeded((succeedEvent) -> {
            long totalNs = Duration.between(now, Instant.now()).getNano();
            LOG.info("Succeeded calculating project statistics. Total time: {} ms", totalNs/1000000);
            ProjectStatistics projectStatistics = (ProjectStatistics) succeedEvent.getSource().getValue();
            LOG.info("Project statistics: " + projectStatistics);
            fillProjectStatistics(projectStatistics);
        });

        task.setOnFailed((failedEvent) -> LOG.error("Error calculating project statistics", failedEvent.getSource().getException()));

        ExecutorService executorService
                = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();
    }

    private void fillProjectStatistics(ProjectStatistics projectStatistics) {
        changedLinesOfCode.setText(String.valueOf(projectStatistics.getChangedLinesOfCode()));
        reviewers.setText(String.valueOf(projectStatistics.getReviewers()));
        reviews.setText(String.valueOf(projectStatistics.getReviews()));
        comments.setText(String.valueOf(projectStatistics.getComments()));
        changedFiles.setText(String.valueOf(projectStatistics.getChangedFiles()));
        commentsPerReview.setText(String.format("%.2f", projectStatistics.getCommentsPerReview()));
        averageReviewTimeInMinutes.setText(String.format("%.0fm", projectStatistics.getAverageReviewTimeInMinutes()));
        commentedFilesPercentage.setText(String.format("%.2f%%", projectStatistics.getCommentedFilesPercentage() * 100));
        averageCommentSize.setText(String.format("%.2f", projectStatistics.getAverageCommentSize()));
    }
}
