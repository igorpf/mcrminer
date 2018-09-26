package com.mcrminer.model;

import com.mcrminer.model.enums.FileStatus;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public final class File {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Diff diff;
    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<Comment> comments;

    private String newFilename;
    private String oldFilename; //if renamed
    private Integer linesInserted;
    private Integer linesRemoved;
    private FileStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Diff getDiff() {
        return diff;
    }

    public void setDiff(Diff diff) {
        this.diff = diff;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public String getNewFilename() {
        return newFilename;
    }

    public void setNewFilename(String newFilename) {
        this.newFilename = newFilename;
    }

    public String getOldFilename() {
        return oldFilename;
    }

    public void setOldFilename(String oldFilename) {
        this.oldFilename = oldFilename;
    }

    public Integer getLinesInserted() {
        return linesInserted;
    }

    public void setLinesInserted(Integer linesInserted) {
        this.linesInserted = linesInserted;
    }

    public Integer getLinesRemoved() {
        return linesRemoved;
    }

    public void setLinesRemoved(Integer linesRemoved) {
        this.linesRemoved = linesRemoved;
    }

    public FileStatus getStatus() {
        return status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;
        File file = (File) o;
        return Objects.equals(getId(), file.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
