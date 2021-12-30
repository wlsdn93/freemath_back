package com.math.weakness.repository;

import com.math.weakness.dto.ProblemShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProblemRepository {
    Page<ProblemShow> findByDifficultyAndStatus(Long id, Pageable pageable,
                                                Integer difficulty, Boolean status);
}
