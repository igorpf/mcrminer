package com.mcrminer.export.impl;

import com.mcrminer.export.FileWriterService;
import com.mcrminer.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.export.PerspectiveService;
import com.mcrminer.export.perspectives.PerspectiveExportService;
import com.mcrminer.export.perspectives.enums.PerspectiveType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@SuppressWarnings("unchecked")
public abstract class AbstractPerspectiveExportService implements PerspectiveExportService {

    private FileWriterService fileWriterService;

    @Override
    public void exportPerspective(PerspectiveExportConfigurationParameters parameters) {
        PerspectiveService perspectiveService = getPerspectiveServiceFor(parameters.getPerspectiveType());
        List<Object> rootEntities = perspectiveService.getRootEntitiesForProject(parameters.getProjectId());
        List<Object> perspectives = new ArrayList<>();
        rootEntities.forEach(entity -> {
            perspectives.add(perspectiveService.createPerspective(entity));
        });
        OutputStream outputStream = getOutputStream(parameters);
        PrintWriter printWriter = new PrintWriter(outputStream);
        fileWriterService.exportAsCsv(printWriter, perspectives, parameters);
    }

    @Autowired
    public void setFileWriterService(FileWriterService fileWriterService) {
        this.fileWriterService = fileWriterService;
    }

    protected abstract PerspectiveService<? ,?> getPerspectiveServiceFor(PerspectiveType perspectiveType);
    protected abstract OutputStream getOutputStream(PerspectiveExportConfigurationParameters parameters);
}
