package com.mcrminer.persistence.repository;

import com.mcrminer.persistence.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository <Project, Long> {

    @EntityGraph(value = "withReviewRequests", type = EntityGraph.EntityGraphType.FETCH)
    Project getById(@Param("id") Long id);

    boolean existsByNameAndUrlPath(String name, String urlPath);
}
