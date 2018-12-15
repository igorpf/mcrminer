package com.mcrminer.persistence.repository;

import com.mcrminer.persistence.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByFileDiffReviewRequestProjectId(Long projectId);

    @Modifying
    @Transactional
    void deleteAllByFileDiffReviewRequestProjectId(Long projectId);
}
