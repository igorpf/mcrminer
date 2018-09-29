package com.mcrminer.model.projections;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
@RequiredArgsConstructor
public class ReviewProjection {
    LocalDateTime createdTime, updatedTime;
    String authorEmail, statusLabel, description;
    Integer reviewedId, id;
}
