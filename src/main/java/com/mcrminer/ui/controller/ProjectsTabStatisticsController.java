package com.mcrminer.ui.controller;

import com.mcrminer.model.Project;
import com.mcrminer.statistics.StatisticsService;
import com.mcrminer.statistics.impl.project.ProjectStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectsTabStatisticsController {

    private final StatisticsService<Project, ProjectStatistics> projectStatisticsService;

    @Autowired
    public ProjectsTabStatisticsController(StatisticsService<Project, ProjectStatistics> projectStatisticsService) {
        this.projectStatisticsService = projectStatisticsService;
    }

    private Project selectedProject;

    void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
        fillProjectStatistics(selectedProject);
    }

    private void fillProjectStatistics(Project project) {
        ProjectStatistics statistics = projectStatisticsService.calculateStatistics(project);
        System.out.println();
    }
}
