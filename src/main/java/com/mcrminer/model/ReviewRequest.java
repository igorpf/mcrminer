package com.mcrminer.model;

import com.mcrminer.model.enums.ReviewRequestStatus;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public final class ReviewRequest extends Reviewable {

//    private User submitter;
    @OneToMany
    private Set<Diff> diffs;
//    private Set<Review> reviews;
    @ManyToOne
    private Project project;
    @Id
    @GeneratedValue
    private Integer id;
//    private String branch;
//    private String commitId;
//    private boolean isPublic;
//    private ReviewRequestStatus status;
//    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
