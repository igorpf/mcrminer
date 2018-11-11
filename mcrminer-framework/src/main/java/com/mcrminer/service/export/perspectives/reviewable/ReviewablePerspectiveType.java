package com.mcrminer.service.export.perspectives.reviewable;

import com.mcrminer.service.export.PerspectiveService;
import com.mcrminer.service.export.PerspectiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewablePerspectiveType implements PerspectiveType {

    private final ReviewablePerspectiveService reviewablePerspectiveService;

    @Autowired
    public ReviewablePerspectiveType(ReviewablePerspectiveService reviewablePerspectiveService) {
        this.reviewablePerspectiveService = reviewablePerspectiveService;
    }

    @Override
    public String getTitleLocalizationKey() {
        return "perspective.reviewable";
    }

    @Override
    public Class<?> getPerspectiveClass() {
        return ReviewablePerspective.class;
    }

    @Override
    public PerspectiveService<?, ?> getPerspectiveService() {
        return reviewablePerspectiveService;
    }
}
