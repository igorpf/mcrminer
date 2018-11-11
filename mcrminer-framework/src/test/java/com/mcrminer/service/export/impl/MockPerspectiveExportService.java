package com.mcrminer.service.export.impl;

import com.mcrminer.service.export.PerspectiveExportConfigurationParameters;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class MockPerspectiveExportService extends AbstractPerspectiveExportService {

    private ByteArrayOutputStream outputStream;

    public MockPerspectiveExportService() {
        super(new DefaultFileWriterService());
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    protected OutputStream getOutputStream(PerspectiveExportConfigurationParameters parameters) {
        return outputStream;
    }
}
