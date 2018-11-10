package com.mcrminer.ui.controller;

import com.mcrminer.persistence.model.Project;
import com.mcrminer.service.export.DefaultPerspectiveExportConfigurationParameters;
import com.mcrminer.service.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.service.export.PerspectiveType;
import com.mcrminer.service.export.perspectives.PerspectiveExportService;
import com.mcrminer.ui.Selectable;
import com.mcrminer.ui.localization.LocalizationService;
import com.mcrminer.ui.perspectives.PerspectiveChoice;
import com.mcrminer.ui.tasks.ExportPerspectiveTask;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class ProjectsTabExportController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectsTabExportController.class);
    private final LocalizationService localizationService;
    private final PerspectiveExportService perspectiveExportService;
    private final List<PerspectiveType> perspectiveTypes;

    @Autowired
    public ProjectsTabExportController(LocalizationService localizationService,
                                       PerspectiveExportService perspectiveExportService,
                                       List<PerspectiveType> perspectiveTypes) {
        this.localizationService = localizationService;
        this.perspectiveExportService = perspectiveExportService;
        this.perspectiveTypes = perspectiveTypes;
    }

    @FXML
    private ComboBox<PerspectiveChoice> perspectivesChoiceBox;
    @FXML
    private ListView<Selectable<String>> perspectiveAttributesList;
    @FXML
    private TextField quote, escape, lineEnd, separator;
    @FXML
    private Button exportButton;
    @FXML
    private Label filenameLabel;
    private HBox mainHBox;

    private Project selectedProject;

    @FXML
    public void initialize() {
        fillPerspectiveChoices();
    }

    public void openFileDialog() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(mainHBox.getScene().getWindow());
        if (file != null) {
            String fileAsString = file.toString();
            filenameLabel.setText(fileAsString);
            setExportButtonAvailability();
        }
    }

    private void setExportButtonAvailability() {
        boolean buttonAvailability = !(selectedProject != null && filenameLabel.getText() != null && !filenameLabel.getText().isEmpty() && perspectivesChoiceBox.getSelectionModel().getSelectedItem() != null);
        exportButton.setDisable(buttonAvailability);
    }

    public void exportFile() {
        ExportPerspectiveTask task = new ExportPerspectiveTask(perspectiveExportService, getExportParams());
        final Instant now = Instant.now();
        exportButton.setDisable(true);

        task.setOnSucceeded((succeedEvent) -> {
            long totalNs = Duration.between(now, Instant.now()).getNano();
            LOG.info("Succeeded exporting project statistics. Total time: {} ms", totalNs/1000000);
            exportButton.setDisable(false);
        });

        task.setOnFailed((failedEvent) -> {
            LOG.error("Error exporting perspective", failedEvent.getSource().getException());
            exportButton.setDisable(false);
        });

        ExecutorService executorService
                = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();
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
        List<String> selectedColumns = perspectiveAttributesList.getItems().stream().filter(Selectable::isSelected).map(Selectable::getValue).collect(Collectors.toList());
        String[] columns = new String[selectedColumns.size()];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = selectedColumns.get(i);
        }
        return columns;
    }

    private void fillPerspectiveAttributes(PerspectiveChoice choice) {
        List<Selectable<String>> columnNames = Arrays
                .stream(choice.getPerspectiveClass().getDeclaredFields())
                .map(Field::getName)
                .map(Selectable::new)
                .collect(Collectors.toList());
        perspectiveAttributesList.setItems(FXCollections.observableList(columnNames));

        perspectiveAttributesList.setCellFactory(CheckBoxListCell.forListView(Selectable::getSelected));
    }

    private void fillPerspectiveChoices() {
        List<PerspectiveChoice> choices = perspectiveTypes
                .stream()
                .map(
                        perspectiveType -> PerspectiveChoice
                                .builder()
                                .perspectiveClass(perspectiveType.getPerspectiveClass())
                                .title(perspectiveType.getTitleLocalizationKey())
                                .perspectiveType(perspectiveType)
                                .build()
                )
                .collect(Collectors.toList());
        choices.forEach(choice -> choice.setTitle(localizationService.getMessage(choice.getTitle())));
        perspectivesChoiceBox.setItems(FXCollections.observableList(choices));
        perspectivesChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    fillPerspectiveAttributes(perspectivesChoiceBox.getItems().get(newValue.intValue()));
                    setExportButtonAvailability();
                }
        );
    }

    void setMainHBox(HBox hBox) {
        this.mainHBox = hBox;
    }

    void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
        setExportButtonAvailability();
    }
}
