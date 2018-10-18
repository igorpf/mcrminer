package com.mcrminer.export.impl;

import com.mcrminer.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.export.PerspectiveService;
import com.mcrminer.export.perspectives.comment.CommentPerspectiveService;
import com.mcrminer.export.perspectives.enums.PerspectiveType;
import com.mcrminer.export.perspectives.reviewable.ReviewablePerspectiveService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultPerspectiveExportService extends AbstractPerspectiveExportService {

    private final ReviewablePerspectiveService reviewablePerspectiveService;
    private final CommentPerspectiveService commentPerspectiveService;

    @Override
    protected PerspectiveService<?, ?> getPerspectiveServiceFor(PerspectiveType perspectiveType) {
        switch (perspectiveType) {
            case REVIEWABLE:
                return reviewablePerspectiveService;
            case COMMENT:
                return commentPerspectiveService;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    protected OutputStream getOutputStream(PerspectiveExportConfigurationParameters parameters) {
        try {
            return new FileOutputStream(parameters.getFilename());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
