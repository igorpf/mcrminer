package com.mcrminer.repository;

import com.mcrminer.model.Project;
import com.mcrminer.model.projections.ProjectProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository <Project, Integer> {

    @EntityGraph(value = "withReviewRequests", type = EntityGraph.EntityGraphType.FETCH)
    ProjectProjection getById(Integer id);
}
