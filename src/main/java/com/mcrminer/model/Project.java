package com.mcrminer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.Set;

@Entity
public final class Project {

    @Id
    @GeneratedValue
    private Integer id;
    private String urlPath;
    private String name;

//    private Set<PullRequest> pullRequests;

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

//    public Set<PullRequest> getPullRequests() {
//        return pullRequests;
//    }
//
//    public void setPullRequests(Set<PullRequest> pullRequests) {
//        this.pullRequests = pullRequests;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj instanceof Project) {
            Project other = (Project) obj;
            return Objects.equals(id, other.id);
        }
        return false;
    }
}
