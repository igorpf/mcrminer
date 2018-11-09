package com.mcrminer.persistence.repository;

import com.mcrminer.persistence.model.ApprovalStatus;
import com.mcrminer.persistence.model.projections.ApprovalStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, String> {
    ApprovalStatusProjection getByLabel(String label);
    List<ApprovalStatusProjection> getAllBy();
}
