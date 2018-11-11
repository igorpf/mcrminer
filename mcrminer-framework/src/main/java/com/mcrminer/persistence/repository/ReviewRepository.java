package com.mcrminer.persistence.repository;

import com.mcrminer.persistence.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByReviewedId(Long reviewedId);
}
