package com.mcrminer.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public final class Diff extends Reviewable {

    @ManyToOne
    private ReviewRequest reviewRequest;
    @OneToMany
    private Set<Review> reviews;
    @OneToMany
    private Set<File> files;

    public ReviewRequest getReviewRequest() {
        return reviewRequest;
    }

    public void setReviewRequest(ReviewRequest reviewRequest) {
        this.reviewRequest = reviewRequest;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }
}
