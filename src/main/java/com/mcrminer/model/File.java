package com.mcrminer.model;

import com.mcrminer.model.enums.FileStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
