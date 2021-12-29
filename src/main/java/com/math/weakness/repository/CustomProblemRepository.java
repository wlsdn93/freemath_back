package com.math.weakness.repository;

import com.math.weakness.domain.Problem;
import com.math.weakness.domain.ProblemShow;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CustomProblemRepository {
    Page<ProblemShow> SearchProblemsWithStatus(Long id, Pageable pageable);
}
