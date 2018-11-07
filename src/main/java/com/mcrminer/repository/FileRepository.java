package com.mcrminer.repository;

import com.mcrminer.model.File;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    @EntityGraph(value = "withComments", type = EntityGraph.EntityGraphType.FETCH)
    List<File> findAllByDiffReviewRequestProjectId(Long projectId);

    @EntityGraph(value = "withComments", type = EntityGraph.EntityGraphType.FETCH)
    List<File> findAllByDiffId(Long diffId);

    @EntityGraph(value = "withComments", type = EntityGraph.EntityGraphType.FETCH)
    List<File> findAllByDiffReviewRequestId(Long reviewRequestId);
}
