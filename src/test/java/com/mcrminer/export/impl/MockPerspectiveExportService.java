package com.mcrminer.export.impl;

import com.mcrminer.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.export.PerspectiveService;
import com.mcrminer.export.perspectives.comment.CommentAssociationsPerspectiveCreationStrategy;
import com.mcrminer.export.perspectives.comment.MockCommentPerspectiveService;
import com.mcrminer.export.perspectives.enums.PerspectiveType;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collections;

public class MockPerspectiveExportService extends AbstractPerspectiveExportService {

    private ByteArrayOutputStream outputStream;

    public MockPerspectiveExportService() {
        super(new DefaultFileWriterService());
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    protected PerspectiveService<?, ?> getPerspectiveServiceFor(PerspectiveType perspectiveType) {
        return new MockCommentPerspectiveService(Collections.singletonList(new CommentAssociationsPerspectiveCreationStrategy()));
    }

    @Override
    protected OutputStream getOutputStream(PerspectiveExportConfigurationParameters parameters) {
        return outputStream;
    }
}
