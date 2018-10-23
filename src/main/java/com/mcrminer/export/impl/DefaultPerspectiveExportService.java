package com.mcrminer.export.impl;

import com.mcrminer.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.export.PerspectiveService;
import com.mcrminer.export.perspectives.author.AuthorPerspectiveService;
import com.mcrminer.export.perspectives.comment.CommentPerspectiveService;
import com.mcrminer.export.perspectives.enums.PerspectiveType;
import com.mcrminer.export.perspectives.file.FilePerspectiveService;
import com.mcrminer.export.perspectives.reviewable.ReviewablePerspectiveService;
import com.mcrminer.export.perspectives.reviewer.ReviewerPerspectiveService;
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
    private final FilePerspectiveService filePerspectiveService;
    private final AuthorPerspectiveService authorPerspectiveService;
    private final ReviewerPerspectiveService reviewerPerspectiveService;

    @Override
    protected PerspectiveService<?, ?> getPerspectiveServiceFor(PerspectiveType perspectiveType) {
        switch (perspectiveType) {
            case REVIEWABLE:
                return reviewablePerspectiveService;
            case COMMENT:
                return commentPerspectiveService;
            case FILE:
                return filePerspectiveService;
            case AUTHOR:
                return authorPerspectiveService;
            case REVIEWER:
                return reviewerPerspectiveService;
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
