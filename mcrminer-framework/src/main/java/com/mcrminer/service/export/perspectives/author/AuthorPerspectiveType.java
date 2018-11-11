package com.mcrminer.service.export.perspectives.author;

import com.mcrminer.service.export.PerspectiveService;
import com.mcrminer.service.export.PerspectiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorPerspectiveType implements PerspectiveType {

    private final AuthorPerspectiveService authorPerspectiveService;

    @Autowired
    public AuthorPerspectiveType(AuthorPerspectiveService authorPerspectiveService) {
        this.authorPerspectiveService = authorPerspectiveService;
    }

    @Override
    public String getTitleLocalizationKey() {
        return "perspective.author";
    }

    @Override
    public Class<?> getPerspectiveClass() {
        return AuthorPerspective.class;
    }

    @Override
    public PerspectiveService<?, ?> getPerspectiveService() {
        return authorPerspectiveService;
    }
}
