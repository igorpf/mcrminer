package com.mcrminer.persistence.model.projections;

import lombok.*;

@Builder
@Value
@RequiredArgsConstructor
public class ApprovalStatusProjection {
    String label, description;
    Integer value;
    @Getter(AccessLevel.NONE)
    boolean isApproval, isVeto;

    public boolean getIsApproval() {
        return isApproval;
    }

    public boolean getIsVeto() {
        return isVeto;
    }
}
