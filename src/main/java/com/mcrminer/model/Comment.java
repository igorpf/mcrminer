package com.mcrminer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public final class Comment extends BaseAuditingEntity {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    private File file;
    @ManyToOne
    private User author;
    @Lob
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
