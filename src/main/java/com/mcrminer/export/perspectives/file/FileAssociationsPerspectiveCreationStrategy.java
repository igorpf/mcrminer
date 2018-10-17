package com.mcrminer.export.perspectives.file;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.File;
import com.mcrminer.model.Project;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.model.User;
import org.springframework.stereotype.Service;

@Service
public class FileAssociationsPerspectiveCreationStrategy implements PerspectiveCreationStrategy <File, FilePerspective> {
    @Override
    public void fillPerspective(File file, FilePerspective perspective) {
        perspective.setNewFilename(file.getNewFilename());
        perspective.setOldFilename(file.getOldFilename());
        perspective.setId(file.getId());
        fillReviewRequestSubmitter(perspective, file.getDiff().getReviewRequest().getSubmitter());
        fillReviewRequest(file, perspective);
        fillProject(file, perspective);
    }

    private void fillReviewRequestSubmitter(FilePerspective perspective, User user) {
        if (user != null) {
            perspective.setReviewRequestSubmitterEmail(user.getEmail());
            perspective.setReviewRequestSubmitterFullname(user.getFullname());
            perspective.setReviewRequestSubmitterUsername(user.getUsername());
        }
    }

    private void fillReviewRequest(File file, FilePerspective perspective) {
        ReviewRequest reviewRequest = file.getDiff().getReviewRequest();
        perspective.setReviewRequestId(reviewRequest.getId());
        perspective.setBranch(reviewRequest.getBranch());
        perspective.setCommitId(reviewRequest.getCommitId());
        perspective.setPublic(reviewRequest.isPublic());
        perspective.setReviewRequestStatus(reviewRequest.getStatus());
        perspective.setDescription(reviewRequest.getDescription());
    }

    private void fillProject(File file, FilePerspective perspective) {
        Project project = file.getDiff().getReviewRequest().getProject();
        perspective.setProjectId(project.getId());
        perspective.setCodeReviewToolId(project.getCodeReviewToolId());
        perspective.setUrlPath(project.getUrlPath());
        perspective.setName(project.getName());
    }
}
