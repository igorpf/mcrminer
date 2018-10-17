package com.mcrminer.repository;

import com.mcrminer.model.ReviewRequest;
import com.mcrminer.model.projections.ReviewRequestProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRequestRepository extends JpaRepository<ReviewRequest, Long> {
    ReviewRequestProjection getById(Long id);
    List<ReviewRequest> findAllByProjectId(Long projectId);
}
