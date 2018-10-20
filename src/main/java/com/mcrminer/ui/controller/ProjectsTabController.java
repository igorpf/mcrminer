package com.mcrminer.ui.controller;

import com.mcrminer.export.perspectives.author.AuthorPerspective;
import com.mcrminer.export.perspectives.comment.CommentPerspective;
import com.mcrminer.export.perspectives.enums.PerspectiveType;
import com.mcrminer.export.perspectives.file.FilePerspective;
import com.mcrminer.export.perspectives.reviewable.ReviewablePerspective;
import com.mcrminer.export.perspectives.reviewer.ReviewerPerspective;
import com.mcrminer.model.Project;
import com.mcrminer.repository.ProjectRepository;
import com.mcrminer.ui.localization.LocalizationService;
import com.mcrminer.ui.perspectives.PerspectiveChoice;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectsTabController {

    private ProjectRepository projectRepository;
    private LocalizationService localizationService;

    @Autowired
    public ProjectsTabController(ProjectRepository projectRepository,
                                 LocalizationService localizationService) {
        this.projectRepository = projectRepository;
        this.localizationService = localizationService;
    }

    @FXML
    private ListView<Project> projectList;
    @FXML
    private ComboBox<PerspectiveChoice> perspectivesChoiceBox;
    @FXML
    private ListView<String> perspectiveAttributesList;
    @FXML
    private TextField filename, quote, escape, lineEnd, separator;

    @FXML
    public void initialize() {
        fillAllProjects();
        fillPerspectiveChoices();
    }

    private void fillPerspectiveAttributes(PerspectiveChoice choice) {
        List<String> columnNames = Arrays.stream(choice.getPerspectiveClass().getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        perspectiveAttributesList.setItems(FXCollections.observableList(columnNames));

        perspectiveAttributesList.setCellFactory(CheckBoxListCell.forListView(param -> {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.setValue(true);
            return observable;
        }));
    }

    private void fillPerspectiveChoices() {
        List<PerspectiveChoice> choices = Arrays.asList(
                PerspectiveChoice.builder().perspectiveClass(AuthorPerspective.class).perspectiveType(PerspectiveType.AUTHOR).title("tab.projects.export.select.author.title").build(),
                PerspectiveChoice.builder().perspectiveClass(CommentPerspective.class).perspectiveType(PerspectiveType.COMMENT).title("tab.projects.export.select.comment.title").build(),
                PerspectiveChoice.builder().perspectiveClass(FilePerspective.class).perspectiveType(PerspectiveType.FILE).title("tab.projects.export.select.file.title").build(),
                PerspectiveChoice.builder().perspectiveClass(ReviewablePerspective.class).perspectiveType(PerspectiveType.REVIEWABLE).title("tab.projects.export.select.reviewable.title").build(),
                PerspectiveChoice.builder().perspectiveClass(ReviewerPerspective.class).perspectiveType(PerspectiveType.REVIEWER).title("tab.projects.export.select.reviewer.title").build()
        );
        choices.forEach(choice -> choice.setTitle(localizationService.getMessage(choice.getTitle())));
        perspectivesChoiceBox.setItems(FXCollections.observableList(choices));
        perspectivesChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> fillPerspectiveAttributes(perspectivesChoiceBox.getItems().get(newValue.intValue()))
        );
    }

    private void fillAllProjects() {
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
    }
}
