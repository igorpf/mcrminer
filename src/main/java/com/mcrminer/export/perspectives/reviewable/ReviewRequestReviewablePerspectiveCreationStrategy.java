package com.mcrminer.export.perspectives.reviewable;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.*;
import com.mcrminer.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ReviewRequestReviewablePerspectiveCreationStrategy implements PerspectiveCreationStrategy<Reviewable, ReviewablePerspective> {

    private final FileRepository fileRepository;

    @Autowired
    public ReviewRequestReviewablePerspectiveCreationStrategy(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void fillPerspective(Reviewable rootEntity, ReviewablePerspective perspective) {
        if (rootEntity instanceof ReviewRequest) {
            ReviewRequest reviewRequest = (ReviewRequest) rootEntity;perspective.setId(reviewRequest.getId());
            perspective.setBranch(reviewRequest.getBranch());
            perspective.setUpdatedTime(reviewRequest.getUpdatedTime());
            perspective.setCreatedTime(reviewRequest.getCreatedTime());
            perspective.setReviews((long) reviewRequest.getReviews().size());
            perspective.setApprovals(reviewRequest.getReviews().stream().map(Review::getStatus).filter(ApprovalStatus::isApproval).count());
            perspective.setVetos(reviewRequest.getReviews().stream().map(Review::getStatus).filter(ApprovalStatus::isVeto).count());
            perspective.setStatus(reviewRequest.getStatus().name());
            fillProject(perspective, reviewRequest);
            fillFileAttributes(perspective, reviewRequest);
        }
    }

    private void fillProject(ReviewablePerspective perspective, ReviewRequest reviewRequest) {
        Project project = reviewRequest.getProject();
        perspective.setProjectId(project.getId());
        perspective.setCodeReviewToolId(project.getCodeReviewToolId());
        perspective.setUrlPath(project.getUrlPath());
        perspective.setName(project.getName());
    }

    private void fillFileAttributes(ReviewablePerspective perspective, ReviewRequest reviewRequest) {
        Collection<File> files = fileRepository.findAllByDiffReviewRequestId(reviewRequest.getId());
        perspective.setFiles((long) files.size());
        perspective.setComments(
                (long) files.stream().map(file -> file.getComments().size()).reduce(Integer::sum).orElse(0)
        );
    }
}
