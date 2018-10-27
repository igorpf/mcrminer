package com.mcrminer.repository;

import com.mcrminer.model.ReviewRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRequestRepository extends JpaRepository<ReviewRequest, Long> {

    @EntityGraph(value = "reviewRequestWithReviews", type = EntityGraph.EntityGraphType.FETCH)
    List<ReviewRequest> findAllByProjectId(Long projectId);

}
