package com.mcrminer.model.projections;

import com.mcrminer.model.enums.ReviewRequestStatus;
import lombok.*;

@Builder
@Value
@RequiredArgsConstructor
public class ReviewRequestProjection {
    Integer projectId;
    String branch, commitId, description, submitterEmail;
    @Getter(AccessLevel.NONE)
    boolean isPublic;
    ReviewRequestStatus status;

    public boolean getIsPublic() {
        return isPublic;
    }
}
