package com.mcrminer.persistence.model;

import com.mcrminer.persistence.model.enums.FileStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@NamedEntityGraphs({
        @NamedEntityGraph(name = "withComments", attributeNodes = {
                @NamedAttributeNode("comments")
        })
})
public final class File {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Diff diff;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "FILE_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Comment> comments;

    public File() {
        this.comments = new HashSet<>();
    }

    private String newFilename;
    private String oldFilename; //if renamed
    private Long linesInserted;
    private Long linesRemoved;
    private FileStatus status;

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
