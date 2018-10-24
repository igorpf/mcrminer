package com.mcrminer.statistics.impl.project;

import com.mcrminer.model.Project;
import com.mcrminer.statistics.StatisticsCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class ProjectStatisticsCalculationStrategy implements StatisticsCalculationStrategy<Project, ProjectStatistics> {
    @Override
    public void calculate(Project project, ProjectStatistics projectStatistics) {

    }
}
