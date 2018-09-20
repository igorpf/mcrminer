package com.mcrminer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public final class Comment extends BaseAuditingEntity {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private File file;
    @ManyToOne
    private User author;
    private String text;


}
