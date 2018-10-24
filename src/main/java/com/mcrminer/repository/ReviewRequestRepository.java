package com.mcrminer.repository;

import com.mcrminer.model.ReviewRequest;
import com.mcrminer.model.projections.ReviewRequestProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRequestRepository extends JpaRepository<ReviewRequest, Long> {
    ReviewRequestProjection getById(Long id);
    List<ReviewRequest> findAllByProjectId(Long projectId);

    @Transactional
    @Modifying
    void deleteAllByProjectId(Long projectId);
}
