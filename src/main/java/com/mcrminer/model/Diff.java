package com.mcrminer.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public final class Diff extends Reviewable {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private ReviewRequest reviewRequest;


//    private Set<File> files;
}
