package com.mcrminer.statistics.impl.project;

import com.mcrminer.model.Project;
import com.mcrminer.statistics.StatisticsCalculationStrategy;
import com.mcrminer.statistics.impl.AbstractStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectStatisticsService extends AbstractStatisticsService<Project, ProjectStatistics> {

    private final List<StatisticsCalculationStrategy<Project, ProjectStatistics>> strategies;

    @Autowired
    public ProjectStatisticsService(List<StatisticsCalculationStrategy<Project, ProjectStatistics>> strategies) {
        super(ProjectStatistics.class);
        this.strategies = strategies;
    }

    @Override
    protected Iterable<StatisticsCalculationStrategy<Project, ProjectStatistics>> getCalculationStrategies() {
        return strategies;
    }
}
