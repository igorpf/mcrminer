package com.mcrminer.repository;

import com.mcrminer.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByFileDiffReviewRequestProjectId(Long projectId);
}
