package com.mcrminer.persistence.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedEntityGraphs({
    @NamedEntityGraph(name = "withReviewRequests", attributeNodes = {
            @NamedAttributeNode("reviewRequests")
    })
})
@Table(
    uniqueConstraints= {@UniqueConstraint(columnNames = {"name", "urlPath"})}
)
public final class Project {

    @Id
    @GeneratedValue
    private Long id;
    private String codeReviewToolId;
    private String urlPath;
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "PROJECT_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ReviewRequest> reviewRequests;

    public Project() {
        this.reviewRequests = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeReviewToolId() {
        return codeReviewToolId;
    }

    public void setCodeReviewToolId(String codeReviewToolId) {
        this.codeReviewToolId = codeReviewToolId;
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

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
