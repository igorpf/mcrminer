package com.mcrminer.ui.tasks;

import com.mcrminer.model.Project;
import com.mcrminer.statistics.StatisticsService;
import com.mcrminer.statistics.impl.project.ProjectStatistics;
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
