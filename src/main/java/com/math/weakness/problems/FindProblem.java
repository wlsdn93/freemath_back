package com.math.weakness.problems;

import com.math.weakness.problems.rdbms.ProblemRdbmsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface FindProblem {
    List<Long> findIdBy(FindRequest request);

    @Service
    @RequiredArgsConstructor
    class FindProblemUsecase implements FindProblem {
        private final ProblemRdbmsRepository problemRepository;

        @Override
        @Transactional(readOnly = true)
        public List<Long> findIdBy(FindRequest request) {
            if (request.isGuest()) {
                return problemRepository.findId(request.difficulty, request.status, request.subject);
            }
            Long id = problemRepository.findUserId(request.accessToken);
            return problemRepository.findId(id, request.difficulty, request.status, request.subject);
        }

    }

    @RequiredArgsConstructor(staticName = "by")
    class FindRequest{
        private final String accessToken;
        private final Integer difficulty;
        private final String subject;
        private final Boolean status;

        private Boolean isGuest(){
            return accessToken.equals("guest");
        }
    }
}
