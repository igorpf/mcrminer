package com.mcrminer.repository;

import com.mcrminer.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByReviewedId(Long reviewedId);
}
