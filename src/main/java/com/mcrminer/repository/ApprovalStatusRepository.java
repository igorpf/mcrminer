package com.mcrminer.repository;

import com.mcrminer.model.ApprovalStatus;
import com.mcrminer.model.projections.ApprovalStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, String> {
    ApprovalStatusProjection getByLabel(String label);
    List<ApprovalStatusProjection> getAllBy();
}
