package com.mcrminer.service.statistics.impl.project;

import com.mcrminer.persistence.model.Comment;
import com.mcrminer.persistence.model.Project;
import com.mcrminer.persistence.model.ReviewRequest;
import com.mcrminer.persistence.repository.CommentRepository;
import com.mcrminer.persistence.repository.ProjectRepository;
import com.mcrminer.service.statistics.StatisticsCalculationStrategy;
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
                    reviewComments.stream()
                            .filter(comment -> getCreationOrUpdateTime(comment) != null)
                            .map(comment -> Duration.between(reviewCreation,getCreationOrUpdateTime(comment)).toMinutes())
                            .collect(Collectors.toList())
            );
        }
        statistics.setAverageReviewTimeInMinutes(commentsElapsedTimes.stream().mapToLong(Long::longValue).average().orElse(0));
    }

    private LocalDateTime getCreationOrUpdateTime(Comment comment) {
        if (comment.getCreatedTime() != null)
            return comment.getCreatedTime();
        if (comment.getUpdatedTime() != null)
            return comment.getUpdatedTime();
        return null;
    }

}
