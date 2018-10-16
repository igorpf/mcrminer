package com.mcrminer.export.impl;

import com.mcrminer.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.export.PerspectiveService;
import com.mcrminer.export.perspectives.enums.PerspectiveType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultPerspectiveExportService extends AbstractPerspectiveExportService {

    @Override
    protected PerspectiveService<?, ?> getPerspectiveServiceFor(PerspectiveType perspectiveType) {
        return null;
    }

    @Override
    protected OutputStream getOutputStream(PerspectiveExportConfigurationParameters parameters) {
        return null;
    }
}
