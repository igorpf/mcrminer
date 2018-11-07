package com.mcrminer.repository;

import com.mcrminer.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    User findByEmail(String email);

    @Query("SELECT DISTINCT u FROM User u INNER JOIN u.reviewRequests r INNER JOIN r.project p WHERE p.id = :projectId")
    @EntityGraph(value = "userWithReviewRequests", type = EntityGraph.EntityGraphType.FETCH)
    List<User> findAllByReviewRequestsProjectId(@Param("projectId") Long projectId);
}
