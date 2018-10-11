package com.mcrminer.repository;

import com.mcrminer.model.ReviewRequest;
import com.mcrminer.model.projections.ReviewRequestProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRequestRepository extends JpaRepository<ReviewRequest, Long> {
    ReviewRequestProjection getById(Long id);
}
