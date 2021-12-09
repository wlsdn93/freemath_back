package com.math.weakness.repository;

import com.math.weakness.domain.Problem;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository {

    Problem save(Problem problem);
    Optional<Problem> findById(Long id);
    List<Problem> findAll();

}
