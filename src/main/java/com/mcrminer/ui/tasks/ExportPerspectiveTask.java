package com.mcrminer.ui.tasks;

import com.mcrminer.mining.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.mining.export.perspectives.PerspectiveExportService;
import javafx.concurrent.Task;

public class ExportPerspectiveTask extends Task<Void> {

    private final PerspectiveExportService perspectiveExportService;
    private final PerspectiveExportConfigurationParameters exportParams;

    public ExportPerspectiveTask(PerspectiveExportService perspectiveExportService, PerspectiveExportConfigurationParameters exportParams) {
        this.perspectiveExportService = perspectiveExportService;
        this.exportParams = exportParams;
    }

    @Override
    protected Void call() throws Exception {
        perspectiveExportService.exportPerspective(exportParams);
        return null;
    }
}
