package com.mcrminer.repository;

import com.mcrminer.model.Diff;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiffRepository extends JpaRepository<Diff, Long> {

    @EntityGraph(value = "diffWithReviews", type = EntityGraph.EntityGraphType.FETCH)
    List<Diff> findAllByReviewRequestProjectId(Long projectId);
}
