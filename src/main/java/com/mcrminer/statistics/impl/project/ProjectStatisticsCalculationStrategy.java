package com.mcrminer.statistics.impl.project;

import com.mcrminer.model.Comment;
import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.repository.CommentRepository;
import com.mcrminer.repository.ProjectRepository;
import com.mcrminer.statistics.StatisticsCalculationStrategy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Order(2)
public class ProjectStatisticsCalculationStrategy implements StatisticsCalculationStrategy<Project, ProjectStatistics> {

    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;

    public ProjectStatisticsCalculationStrategy(ProjectRepository projectRepository, CommentRepository commentRepository) {
        this.projectRepository = projectRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void calculate(Project p, ProjectStatistics statistics) {
        Project project = projectRepository.getById(p.getId());
        long reviews = statistics.getReviews();
        double commentsPerReview = reviews != 0?  statistics.getComments() / (double) statistics.getReviews() : 0;
        statistics.setCommentsPerReview(commentsPerReview);
        calculateAverageReviewTime(project, statistics);
    }

    private void calculateAverageReviewTime(Project project, ProjectStatistics statistics) {
        List<Long> commentsElapsedTimes = new ArrayList<>();
        for (ReviewRequest reviewRequest : project.getReviewRequests()) {
            LocalDateTime reviewCreation = reviewRequest.getCreatedTime();
            List<Comment> reviewComments = commentRepository.findAllByFileDiffReviewRequestProjectId(project.getId());
            commentsElapsedTimes.addAll(
                    reviewComments.stream().map(comment -> Duration.between(reviewCreation,comment.getCreatedTime()).toMinutes()).collect(Collectors.toList())
            );
        }
        statistics.setAverageReviewTimeInMinutes(commentsElapsedTimes.stream().mapToLong(Long::longValue).average().orElse(0));
    }

}
