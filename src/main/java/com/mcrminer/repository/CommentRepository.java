package com.mcrminer.repository;

import com.mcrminer.model.Comment;
import com.mcrminer.model.projections.CommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    CommentProjection getById(Integer id);
    List<CommentProjection> getAllBy();
}
