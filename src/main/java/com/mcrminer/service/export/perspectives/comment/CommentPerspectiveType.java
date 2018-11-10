package com.mcrminer.service.export.perspectives.comment;

import com.mcrminer.service.export.PerspectiveService;
import com.mcrminer.service.export.PerspectiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentPerspectiveType implements PerspectiveType {

    private final CommentPerspectiveService commentPerspectiveService;

    @Autowired
    public CommentPerspectiveType(CommentPerspectiveService commentPerspectiveService) {
        this.commentPerspectiveService = commentPerspectiveService;
    }

    @Override
    public String getTitleLocalizationKey() {
        return "perspective.comment";
    }

    @Override
    public Class<?> getPerspectiveClass() {
        return CommentPerspective.class;
    }

    @Override
    public PerspectiveService<?, ?> getPerspectiveService() {
        return commentPerspectiveService;
    }
}
