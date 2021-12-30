package com.math.weakness.repository;

import com.math.weakness.domain.Problem;
import com.math.weakness.dto.ProblemShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long>, CustomProblemRepository {

    @Override
    Page<ProblemShow> SearchProblemsWithStatus(Long id, Pageable pageable);
}
