package com.mcrminer.export.perspectives.reviewable;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DiffReviewablePerspectiveCreationStrategy implements PerspectiveCreationStrategy<Reviewable, ReviewablePerspective> {

    @Override
    public void fillPerspective(Reviewable rootEntity, ReviewablePerspective perspective) {
        if (rootEntity instanceof Diff) {
            Diff diff = (Diff) rootEntity;
            perspective.setId(diff.getId());
            perspective.setBranch(diff.getReviewRequest().getBranch());
            perspective.setUpdatedTime(diff.getUpdatedTime());
            perspective.setCreatedTime(diff.getCreatedTime());
            perspective.setReviews((long) diff.getReviews().size());
            perspective.setApprovals(diff.getReviews().stream().map(Review::getStatus).filter(ApprovalStatus::isApproval).count());
            perspective.setVetos(diff.getReviews().stream().map(Review::getStatus).filter(ApprovalStatus::isVeto).count());
            perspective.setStatus(diff.getReviewRequest().getStatus().name());
            fillProject(perspective, diff);
            fillFileAttributes(perspective, diff);
        }
    }

    private void fillProject(ReviewablePerspective perspective, Diff diff) {
        Project project = diff.getReviewRequest().getProject();
        perspective.setProjectId(project.getId());
        perspective.setCodeReviewToolId(project.getCodeReviewToolId());
        perspective.setUrlPath(project.getUrlPath());
        perspective.setName(project.getName());
    }

    private void fillFileAttributes(ReviewablePerspective perspective, Diff diff) {
        Collection<File> files = diff.getFiles();
        perspective.setFiles((long) files.size());
        perspective.setComments(
                (long) files.stream().map(file -> file.getComments().size()).reduce(Integer::sum).orElse(0)
        );
    }
}
