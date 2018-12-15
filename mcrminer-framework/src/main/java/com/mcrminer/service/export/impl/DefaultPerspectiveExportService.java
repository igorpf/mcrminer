package com.mcrminer.service.export.impl;

import com.mcrminer.service.export.PerspectiveExportConfigurationParameters;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultPerspectiveExportService extends AbstractPerspectiveExportService {

    @Override
    protected OutputStream getOutputStream(PerspectiveExportConfigurationParameters parameters) {
        try {
            return new FileOutputStream(parameters.getFilename());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
