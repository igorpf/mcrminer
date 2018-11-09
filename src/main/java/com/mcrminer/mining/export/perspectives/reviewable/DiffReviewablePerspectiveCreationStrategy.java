package com.mcrminer.mining.export.perspectives.reviewable;

import com.mcrminer.mining.export.PerspectiveCreationStrategy;
import com.mcrminer.persistence.model.*;
import com.mcrminer.persistence.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class DiffReviewablePerspectiveCreationStrategy implements PerspectiveCreationStrategy<Reviewable, ReviewablePerspective> {

    private final FileRepository fileRepository;

    @Autowired
    public DiffReviewablePerspectiveCreationStrategy(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
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
        Collection<File> files = fileRepository.findAllByDiffId(diff.getId());
        perspective.setFiles((long) files.size());
        perspective.setComments(
                (long) files.stream().map(file -> file.getComments().size()).reduce(Integer::sum).orElse(0)
        );
    }
}
