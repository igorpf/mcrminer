package com.mcrminer.repository;

import com.mcrminer.model.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, String> {
}
