package com.mcrminer.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public final class Comment extends BaseAuditingEntity {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private File file;
    @ManyToOne
    private User author;
    @Lob
    private String text;

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
