package com.mcrminer.model;

import com.mcrminer.model.enums.FileStatus;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public final class File {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Diff diff;
    @OneToMany
    private Collection<Comment> comments;

    private String newFilename;
    private String oldFilename; //if renamed
    private Long linesInserted;
    private Long linesRemoved;
    private FileStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getLinesInserted() {
        return linesInserted;
    }

    public void setLinesInserted(Long linesInserted) {
        this.linesInserted = linesInserted;
    }

    public Long getLinesRemoved() {
        return linesRemoved;
    }

    public void setLinesRemoved(Long linesRemoved) {
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
