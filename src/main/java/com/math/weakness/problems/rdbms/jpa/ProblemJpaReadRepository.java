package com.math.weakness.problems.rdbms.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProblemJpaReadRepository extends JpaRepository<ProblemReadEntity, Long> {

    @Transactional(readOnly = true)
    List<ProblemReadEntity> findAllByDifficultyAndSubjectContains(Integer difficulty, String subject);
}
