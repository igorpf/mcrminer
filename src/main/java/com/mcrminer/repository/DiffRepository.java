package com.mcrminer.repository;

import com.mcrminer.model.Diff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiffRepository extends JpaRepository<Diff, Integer> {
}
