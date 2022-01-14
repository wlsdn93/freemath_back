package com.math.weakness.repository;

import com.math.weakness.config.QuerydslConfig;
import com.math.weakness.dto.ProblemShow;
import org.junit.jupiter.api.Test; // jupiter == 5
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@Import(QuerydslConfig.class)
class CustomProblemRepositoryImplTest {
    @Autowired
    CustomProblemRepositoryImpl repository;

    @Test
    void searchProblemsWithStatus() {
        Long id = 1L;
        PageRequest pageable = PageRequest.of(1, 10);
        int difficulty = 1;
        boolean status = true;
        Page<ProblemShow> problemShows = repository.findByDifficultyAndStatus(id, pageable, difficulty, status);
    }
}