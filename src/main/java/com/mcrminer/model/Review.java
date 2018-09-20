package com.mcrminer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public final class Review extends BaseAuditingEntity {

    @ManyToOne
    private User author;

    @ManyToOne
    private Reviewable reviewed;

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private ApprovalStatus status;
    private String description;
}
