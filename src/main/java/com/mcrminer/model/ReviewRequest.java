package com.mcrminer.model;

import com.mcrminer.model.enums.ReviewRequestStatus;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public final class ReviewRequest extends Reviewable {

    @ManyToOne
    private User submitter;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "reviewRequest")
    private Set<Diff> diffs;
    @ManyToOne
    private Project project;
    private String branch;
    private String commitId;
    private boolean isPublic;
    private ReviewRequestStatus status;
    private String description;

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    public Set<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(Set<Diff> diffs) {
        this.diffs = diffs;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public ReviewRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewRequestStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
