package com.math.weakness.problems;

import com.math.weakness.oauth.service.JwtService;
import com.math.weakness.problems.domain.Problem;
import com.math.weakness.problems.rdbms.promblem.ProblemRepository;
import com.math.weakness.problems.rdbms.userProblem.UserProblemRepository;
import com.math.weakness.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface SaveProblem {

    void update(Long problemId, String answer, String accessToken);

    @Service
    @RequiredArgsConstructor
    class SaveProblemUsecase implements SaveProblem {

        private final JwtService jwtService; // TODO
        private final UserService userService; // TODO
        private final ProblemRepository repository;
        private final UserProblemRepository userProblemRepository;

        @Override
        public void update(Long problemId, String answer, String accessToken) {
            Problem problem = repository.findById(problemId);
            userProblemRepository.updateBy(UserProblemRequest.with(getUserId(accessToken), problemId, problem.isCorrect(answer), answer));
        }
        private Long getUserId(String accessToken) {
            Claims claims = jwtService.parseJwt(accessToken);
            String email = claims.get("email").toString();
            return userService.findByEmail(email);
        }
    }

    @Getter
    @RequiredArgsConstructor(staticName = "with")
    class UserProblemRequest{
        private final Long problemId;
        private final Long userId;
        private final Boolean isCorrect;
        private final String submittedAnswer;
    }
}
