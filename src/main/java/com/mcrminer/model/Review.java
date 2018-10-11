package com.mcrminer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public final class Review extends BaseAuditingEntity {

    @ManyToOne
    private User author;
    @ManyToOne
    private Reviewable reviewed;
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private ApprovalStatus status;
    private String description;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Reviewable getReviewed() {
        return reviewed;
    }

    public void setReviewed(Reviewable reviewed) {
        this.reviewed = reviewed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
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
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
