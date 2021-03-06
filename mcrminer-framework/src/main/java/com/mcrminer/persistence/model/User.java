package com.mcrminer.persistence.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "userWithReviewRequests", attributeNodes = {
                @NamedAttributeNode("reviewRequests")
        })
})
public final class User {

    @Id
    private String email;
    private String fullname;
    private String username;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBMITTER_EMAIL")
    private Set<ReviewRequest> reviewRequests;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<ReviewRequest> getReviewRequests() {
        return reviewRequests;
    }

    public void setReviewRequests(Set<ReviewRequest> reviewRequests) {
        this.reviewRequests = reviewRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEmail());
    }
}
