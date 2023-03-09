package com.math.weakness.problems.rdbms.promblem;

import com.math.weakness.problems.domain.Problem;
import com.math.weakness.problems.rdbms.promblem.jpa.ProblemJpaReadRepository;
import com.math.weakness.problems.rdbms.promblem.jpa.ProblemReadEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

public interface ProblemRepository {
    List<Long> findId(Integer difficulty, Boolean status, String subject);

    Long findUserId(String accessToken);

    List<Long> findId(Long id, Integer difficulty, Boolean status, String subject);

    @Repository
    @RequiredArgsConstructor
    class ProblemRdbmsRepository implements ProblemRepository {
        private final ProblemJpaReadRepository problemJpaReadRepository;

        @Override
        public List<Long> findId(Integer difficulty, Boolean status, String subject) {
            return problemJpaReadRepository.findAllByDifficultyAndSubjectContains(difficulty, subject)
                .stream()
                .map(ProblemReadEntity::getProblemId)
                .collect(Collectors.toList());
        }

        @Override
        public Long findUserId(String accessToken) {
            return null;
        }

        @Override
        public List<Long> findId(Long id, Integer difficulty, Boolean status, String subject) {
            return null;
        }

    }
}
