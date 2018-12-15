package com.mcrminer.persistence.model.projections;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class UserProjection {
    String email, fullname, username;
}
