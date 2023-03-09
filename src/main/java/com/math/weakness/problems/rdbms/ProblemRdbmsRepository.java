package com.math.weakness.problems.rdbms;

import com.math.weakness.problems.rdbms.jpa.ProblemJpaReadRepository;
import com.math.weakness.problems.rdbms.jpa.ProblemReadEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

public interface ProblemRdbmsRepository {
    List<Long> findId(Integer difficulty, Boolean status, String subject);

    Long findUserId(String accessToken);

    List<Long> findId(Long id, Integer difficulty, Boolean status, String subject);

    @Repository
    @RequiredArgsConstructor
    class ProblemRdbmsRepositoryImpl implements ProblemRdbmsRepository {
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
