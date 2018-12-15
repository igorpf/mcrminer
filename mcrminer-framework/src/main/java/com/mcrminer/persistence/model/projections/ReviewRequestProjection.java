package com.mcrminer.persistence.model.projections;

import com.mcrminer.persistence.model.enums.ReviewRequestStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Value
@RequiredArgsConstructor
public class ReviewRequestProjection {
    Integer projectId;
    String branch, commitId, description, submitterEmail;
    @Getter(AccessLevel.NONE)
    boolean isPublic;
    ReviewRequestStatus status;
    LocalDateTime createdTime, updatedTime;

    public boolean getIsPublic() {
        return isPublic;
    }
}
