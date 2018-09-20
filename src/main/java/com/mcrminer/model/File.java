package com.mcrminer.model;

import com.mcrminer.model.enums.FileStatus;

import javax.persistence.*;
import java.util.Set;

@Entity
public final class File {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Diff diff;
    @OneToMany
    private Set<Comment> comments;

    private String newFilename;
    private String oldFilename; //if renamed
    private Integer linesInserted;
    private Integer linesRemoved;
    private FileStatus status;
}
