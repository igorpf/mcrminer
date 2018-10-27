package com.mcrminer.statistics.impl.project;

import com.mcrminer.model.*;
import com.mcrminer.statistics.StatisticsCalculationStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
public class ProjectStatisticsCalculationStrategy implements StatisticsCalculationStrategy<Project, ProjectStatistics> {

    @Override
    @Transactional
    public void calculate(Project project, ProjectStatistics statistics) {
        calculateFileAttributes(project, statistics);
        calculateReviewAttributes(project, statistics);
        statistics.setCommentsPerReview(statistics.getComments() / (double) statistics.getReviews());
        calculateAverageReviewTime(project, statistics);
    }

    private void calculateAverageReviewTime(Project project, ProjectStatistics statistics) {
        List<Long> commentsElapsedTimes = new ArrayList<>();
        for (ReviewRequest reviewRequest : project.getReviewRequests()) {
            LocalDateTime reviewCreation = reviewRequest.getCreatedTime();
            List<Comment> reviewComments = reviewRequest
                    .getDiffs().stream()
                    .map(Diff::getFiles).flatMap(Collection::stream)
                    .map(File::getComments).flatMap(Collection::stream).collect(Collectors.toList());
            commentsElapsedTimes.addAll(
                    reviewComments.stream().map(comment -> Duration.between(reviewCreation,comment.getCreatedTime()).toMinutes()).collect(Collectors.toList())
            );
        }
        statistics.setAverageReviewTimeInMinutes(commentsElapsedTimes.stream().mapToLong(Long::longValue).average().orElse(0));
    }

    private void calculateReviewAttributes(Project project, ProjectStatistics statistics) {
        List<Review> reviewRequestsReviews = project.getReviewRequests().stream().map(ReviewRequest::getReviews).flatMap(Set::stream).collect(Collectors.toList());
        List<Review> diffsReviews = project.getReviewRequests().stream().map(ReviewRequest::getDiffs).flatMap(Set::stream).map(Diff::getReviews).flatMap(Set::stream).collect(Collectors.toList());
        List<Review> allReviews = new ArrayList<>(reviewRequestsReviews);
        allReviews.addAll(diffsReviews);
        List<User> reviewers = allReviews.stream().map(Review::getAuthor).distinct().collect(Collectors.toList());
        statistics.setReviews(reviewRequestsReviews.size() + diffsReviews.size());
        statistics.setReviewers(reviewers.size());
    }

    private void calculateFileAttributes(Project project, ProjectStatistics statistics) {
        List<File> allFiles = project.getReviewRequests().stream().map(ReviewRequest::getDiffs).flatMap(Set::stream).map(Diff::getFiles).flatMap(Collection::stream).collect(Collectors.toList());
        List<File> commentedFiles = allFiles.stream().filter(file -> isNotEmpty(file.getComments())).collect(Collectors.toList());

        statistics.setChangedFiles(allFiles.size());
        statistics.setComments(allFiles.stream().map(File::getComments).mapToLong(Collection::size).sum());
        statistics.setAverageCommentSize(allFiles.stream().map(File::getComments).flatMap(Collection::stream).mapToLong(c -> c.getText().length()).average().orElse(0.0));
        statistics.setChangedLinesOfCode(allFiles.stream().mapToLong(file -> file.getLinesRemoved() + file.getLinesInserted()).sum());
        statistics.setCommentedFilesPercentage(commentedFiles.size() / (double) allFiles.size());
    }
}
