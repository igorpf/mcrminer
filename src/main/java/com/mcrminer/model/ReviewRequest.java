package com.mcrminer.model;

import com.mcrminer.model.enums.ReviewRequestStatus;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public final class ReviewRequest extends Reviewable {

    @ManyToOne
    private User submitter;
    @OneToMany
    private Set<Diff> diffs;
    @OneToMany
    private Set<Review> reviews;
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

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewRequest)) return false;
        ReviewRequest that = (ReviewRequest) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
