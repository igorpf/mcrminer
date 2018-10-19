package com.mcrminer.ui.controller;

import com.mcrminer.model.Project;
import com.mcrminer.repository.ProjectRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectsTabController {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectsTabController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @FXML
    private ListView<Project> projectList;

    @FXML
    public void initialize() {
        projectList.setItems(FXCollections.observableList(
                projectRepository.findAll()
        ));
        projectList.setCellFactory(param -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null)
                    setText(item.getName());
            }
        });
    }
}
