package com.mcrminer.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public final class Diff extends Reviewable {

    @ManyToOne
    private ReviewRequest reviewRequest;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "diff")
    private Collection<File> files;

    public ReviewRequest getReviewRequest() {
        return reviewRequest;
    }

    public void setReviewRequest(ReviewRequest reviewRequest) {
        this.reviewRequest = reviewRequest;
    }

    public Collection<File> getFiles() {
        return files;
    }

    public void setFiles(Collection<File> files) {
        this.files = files;
    }
}
