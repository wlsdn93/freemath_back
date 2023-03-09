package com.math.weakness.problems.rdbms.userProblem;

import com.math.weakness.problems.SaveProblem.UserProblemRequest;
import com.math.weakness.problems.rdbms.userProblem.jpa.UserProblem;
import com.math.weakness.problems.rdbms.userProblem.jpa.UserProblemId;
import com.math.weakness.problems.rdbms.userProblem.jpa.UserProblemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface UserProblemRepository {

    void updateBy(UserProblemRequest request);

    @Repository
    @RequiredArgsConstructor
    class UserProblemRdbmsRepository implements UserProblemRepository{
        private final UserProblemJpaRepository userProblemJpaRepository;

        @Override
        @Transactional
        public void updateBy(UserProblemRequest request) {
            userProblemJpaRepository.findById(new UserProblemId(request.getProblemId(), request.getUserId()))
                    .map(userProblem -> userProblem.update(request.getIsCorrect(), request.getSubmittedAnswer()))
                    .orElseGet(() -> userProblemJpaRepository.save(new UserProblem(request.getProblemId(), request.getUserId(), request.getIsCorrect(), request.getSubmittedAnswer())));
        }
    }


}
