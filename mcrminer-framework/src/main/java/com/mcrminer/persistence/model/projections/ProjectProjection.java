package com.mcrminer.persistence.model.projections;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class ProjectProjection {
    Integer id;
    String urlPath, name;
}
