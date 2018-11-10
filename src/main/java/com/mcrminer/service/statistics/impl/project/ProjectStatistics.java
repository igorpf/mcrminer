package com.mcrminer.service.statistics.impl.project;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProjectStatistics {
    long changedLinesOfCode, reviewers, reviews, comments, changedFiles;
    double commentsPerReview, averageReviewTimeInMinutes, commentedFilesPercentage, averageCommentSize;
}
