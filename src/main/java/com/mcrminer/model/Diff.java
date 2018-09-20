package com.mcrminer.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public final class Diff extends Reviewable {

    @ManyToOne
    private ReviewRequest reviewRequest;
    @OneToMany
    private Set<Review> reviews;

    @OneToMany
    private Set<File> files;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Diff)) return false;
        Diff diff = (Diff) o;
        return Objects.equals(getId(), diff.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
