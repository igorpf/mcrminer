package com.mcrminer.repository;

import com.mcrminer.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByDiffReviewRequestProjectId(Long projectId);

    @Transactional
    @Modifying
    void deleteAllByDiffReviewRequestProjectId(Long projectId);
}
