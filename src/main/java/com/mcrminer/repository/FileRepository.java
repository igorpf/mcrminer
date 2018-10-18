package com.mcrminer.repository;

import com.mcrminer.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByDiffReviewRequestProjectId(Long projectId);
}
