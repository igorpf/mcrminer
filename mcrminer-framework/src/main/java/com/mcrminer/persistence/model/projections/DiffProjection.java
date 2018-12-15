package com.mcrminer.persistence.model.projections;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
@RequiredArgsConstructor
public class DiffProjection {
    Integer id, reviewRequestId;
    LocalDateTime createdTime, updatedTime;
}
