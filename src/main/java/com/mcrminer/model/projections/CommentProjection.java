package com.mcrminer.model.projections;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
@RequiredArgsConstructor
public class CommentProjection {
    Integer id, fileId;
    String authorEmail, text;
    LocalDateTime createdTime, updatedTime;
}
