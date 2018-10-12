package com.mcrminer.model.projections;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentProjection {
    Integer id, fileId;
    String authorEmail, text;
    LocalDateTime createdTime, updatedTime;
}
