package com.mcrminer.repository;

import com.mcrminer.model.Diff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiffRepository extends JpaRepository<Diff, Long> {
    List<Diff> findAllByReviewRequestProjectId(Long projectId);
    void deleteAllByReviewRequestProjectId(Long projectId);
}
