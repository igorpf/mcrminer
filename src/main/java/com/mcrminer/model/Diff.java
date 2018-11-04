package com.mcrminer.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "diffWithReviews", attributeNodes = {
                @NamedAttributeNode("reviews")
        })
})
public final class Diff extends Reviewable {

    @ManyToOne
    private ReviewRequest reviewRequest;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "DIFF_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<File> files;

    public Diff() {
        this.files = new HashSet<>();
    }

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

    @Override
    public String toString() {
        return "Diff{" +
                "reviewRequest=" + reviewRequest +
                '}';
    }
}
