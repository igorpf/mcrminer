package com.mcrminer.ui.controller;

import com.mcrminer.export.DefaultPerspectiveExportConfigurationParameters;
import com.mcrminer.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.export.perspectives.PerspectiveExportService;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectsTabController {

    private final ProjectRepository projectRepository;
    private final LocalizationService localizationService;
    private final PerspectiveExportService perspectiveExportService;

    @Autowired
    public ProjectsTabController(ProjectRepository projectRepository,
                                 LocalizationService localizationService,
                                 PerspectiveExportService perspectiveExportService) {
        this.projectRepository = projectRepository;
        this.localizationService = localizationService;
        this.perspectiveExportService = perspectiveExportService;
    }

    @FXML
    private HBox mainHBox;
    @FXML
    private ListView<Project> projectList;
    @FXML
    private ComboBox<PerspectiveChoice> perspectivesChoiceBox;
    @FXML
    private ListView<String> perspectiveAttributesList;
    @FXML
    private TextField quote, escape, lineEnd, separator;
    @FXML
    private Button exportButton, filenameButton;
    @FXML
    private Label filenameLabel;
    private Project selectedProject;

    @FXML
    public void initialize() {
        fillAllProjects();
        fillPerspectiveChoices();
    }

    public void openFileDialog() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(mainHBox.getScene().getWindow());
        if (file != null) {
            String fileAsString = file.toString();
            filenameLabel.setText(fileAsString);
            System.out.println();
        }
    }

    public void exportFile() {
        PerspectiveExportConfigurationParameters params = getExportParams();
        perspectiveExportService.exportPerspective(params);
    }

    private PerspectiveExportConfigurationParameters getExportParams() {
        return DefaultPerspectiveExportConfigurationParameters
                .builder()
                .projectId(selectedProject != null? selectedProject.getId() : null)
                .columns(getColumns())
                .perspectiveClass(perspectivesChoiceBox.getValue().getPerspectiveClass())
                .perspectiveType(perspectivesChoiceBox.getValue().getPerspectiveType())
                .filename(filenameLabel.getText())
                .escape(getCharFromField(escape))
                .quote(getCharFromField(quote))
                .separator(getCharFromField(separator))
                .lineEnd(lineEnd.getText())
                .build();
    }

    private char getCharFromField(TextField field) {
        String text = field.getText();
        return text != null && text.length() > 0? text.charAt(0) : '\0';
    }

    private String[] getColumns() {
        String[] columns = new String[perspectiveAttributesList.getItems().size()];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = perspectiveAttributesList.getItems().get(i);
        }
        return columns;
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
        projects.addAll(projects);
        projects.addAll(projects);
        projects.addAll(projects);
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
            selectedProject = projectList.getItems().get(newValue.intValue());
        });
    }
}
