package com.math.weakness.repository;

import com.math.weakness.dto.ProblemShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface CustomProblemRepository {

    @Transactional(readOnly = true)
    Page<ProblemShow> findByDifficultyAndStatusAndId(Long id, Pageable pageable, Integer difficulty, Boolean status, String subject);

    @Transactional(readOnly = true)
    Page<ProblemShow> findByDifficultyAndStatus(Pageable pageable, Integer difficulty, Boolean status, String subject);

}
