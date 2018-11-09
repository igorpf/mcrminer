package com.mcrminer.ui.tasks;

import com.mcrminer.mining.statistics.StatisticsService;
import com.mcrminer.mining.statistics.impl.project.ProjectStatistics;
import com.mcrminer.persistence.model.Project;
import javafx.concurrent.Task;

public class CalculateProjectStatisticsTask extends Task<ProjectStatistics> {

    private final StatisticsService<Project, ProjectStatistics> projectStatisticsService;
    private final Project project;

    public CalculateProjectStatisticsTask(StatisticsService<Project, ProjectStatistics> projectStatisticsService, Project project) {
        this.projectStatisticsService = projectStatisticsService;
        this.project = project;
    }

    @Override
    protected ProjectStatistics call() throws Exception {
        return projectStatisticsService.calculateStatistics(project);
    }
}
