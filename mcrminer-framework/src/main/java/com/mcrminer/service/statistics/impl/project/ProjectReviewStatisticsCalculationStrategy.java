package com.mcrminer.service.statistics.impl.project;

import com.mcrminer.persistence.model.*;
import com.mcrminer.persistence.repository.DiffRepository;
import com.mcrminer.persistence.repository.ReviewRequestRepository;
import com.mcrminer.service.statistics.StatisticsCalculationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Order(1)
public class ProjectReviewStatisticsCalculationStrategy implements StatisticsCalculationStrategy<Project, ProjectStatistics> {

    private final DiffRepository diffRepository;
    private final ReviewRequestRepository reviewRequestRepository;

    @Autowired
    public ProjectReviewStatisticsCalculationStrategy(DiffRepository diffRepository, ReviewRequestRepository reviewRequestRepository) {
        this.diffRepository = diffRepository;
        this.reviewRequestRepository = reviewRequestRepository;
    }

    @Override
    public void calculate(Project project, ProjectStatistics statistics) {
        List<ReviewRequest> projectReviewRequests = reviewRequestRepository.findAllByProjectId(project.getId());
        List<Review> reviewRequestsReviews = projectReviewRequests.stream().map(ReviewRequest::getReviews).flatMap(Set::stream).collect(Collectors.toList());

        List<Diff> projectDiffs = diffRepository.findAllByReviewRequestProjectId(project.getId());
        List<Review> diffsReviews = projectDiffs.stream().map(Diff::getReviews).flatMap(Set::stream).collect(Collectors.toList());
        List<Review> allReviews = new ArrayList<>(reviewRequestsReviews);
        allReviews.addAll(diffsReviews);

        List<User> reviewers = allReviews.stream().map(Review::getAuthor).distinct().collect(Collectors.toList());
        statistics.setReviews(reviewRequestsReviews.size() + diffsReviews.size());
        statistics.setReviewers(reviewers.size());
    }
}
