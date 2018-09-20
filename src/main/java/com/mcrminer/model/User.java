package com.mcrminer.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public final class User {

    private String fullname;
    private String username;
    @Id
    private String email;

    @OneToMany
    private Set<ReviewRequest> reviewRequests;
}
