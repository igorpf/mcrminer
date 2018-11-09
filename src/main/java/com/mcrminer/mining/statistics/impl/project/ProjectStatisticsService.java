package com.mcrminer.mining.statistics.impl.project;

import com.mcrminer.mining.statistics.StatisticsCalculationStrategy;
import com.mcrminer.mining.statistics.impl.AbstractStatisticsService;
import com.mcrminer.persistence.model.Project;
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
