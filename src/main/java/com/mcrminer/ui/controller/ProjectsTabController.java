package com.mcrminer.ui.controller;

import com.mcrminer.persistence.model.Project;
import com.mcrminer.persistence.repository.ProjectRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectsTabController {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectsTabController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @FXML
    private ListView<Project> projectList;
    @FXML
    private Button deleteProjectButton;
    @FXML
    private ProjectsTabExportController projectsTabExportController;
    @FXML
    private ProjectsTabStatisticsController projectsTabStatisticsController;
    @FXML
    private HBox mainHBox;

    private Project selectedProject;

    @FXML
    public void initialize() {
        fillAllProjects();
        projectsTabExportController.setMainHBox(mainHBox);
    }

    public void deleteProject() {
        if (selectedProject != null) {
            projectRepository.delete(selectedProject);
            fillAllProjects();
        }
    }

    public void fillAllProjects() {
        List<Project> projects = projectRepository.findAll();
        projectList.setItems(FXCollections.observableList(projects));
        projectList.setCellFactory(param -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null)
                    setText(item.getName());
            }
        });
        projectList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        projectList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int index = newValue.intValue();
            selectedProject = index != -1? projectList.getItems().get(newValue.intValue()) : null;
            projectsTabExportController.setSelectedProject(selectedProject);
            projectsTabStatisticsController.setSelectedProject(selectedProject);
            deleteProjectButton.setDisable(selectedProject == null);
        });
    }
}