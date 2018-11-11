package com.mcrminer.service.export.perspectives.reviewer;

import com.mcrminer.service.export.PerspectiveService;
import com.mcrminer.service.export.PerspectiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewerPerspectiveType implements PerspectiveType {

    private final ReviewerPerspectiveService reviewerPerspectiveService;

    @Autowired
    public ReviewerPerspectiveType(ReviewerPerspectiveService reviewerPerspectiveService) {
        this.reviewerPerspectiveService = reviewerPerspectiveService;
    }

    @Override
    public String getTitleLocalizationKey() {
        return "perspective.reviewer";
    }

    @Override
    public Class<?> getPerspectiveClass() {
        return ReviewerPerspective.class;
    }

    @Override
    public PerspectiveService<?, ?> getPerspectiveService() {
        return reviewerPerspectiveService;
    }
}
