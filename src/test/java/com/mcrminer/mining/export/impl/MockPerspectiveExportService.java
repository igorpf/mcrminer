package com.mcrminer.mining.export.impl;

import com.mcrminer.mining.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.mining.export.PerspectiveService;
import com.mcrminer.mining.export.perspectives.comment.CommentAssociationsPerspectiveCreationStrategy;
import com.mcrminer.mining.export.perspectives.comment.MockCommentPerspectiveService;
import com.mcrminer.mining.export.perspectives.enums.PerspectiveType;

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
