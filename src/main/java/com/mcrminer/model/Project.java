package com.mcrminer.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedEntityGraphs({
    @NamedEntityGraph(name = "withReviewRequests", attributeNodes = {
            @NamedAttributeNode("reviewRequests")
    })
})
public final class Project {

    @Id
    @GeneratedValue
    private Integer id;
    private String urlPath;
    private String name;

    @OneToMany
    private Set<ReviewRequest> reviewRequests;

    public Project() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
