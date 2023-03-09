package com.math.weakness.problems;

import com.math.weakness.oauth.service.JwtService;
import com.math.weakness.problems.domain.Problem;
import com.math.weakness.problems.rdbms.promblem.ProblemRepository;
import com.math.weakness.problems.rdbms.userProblem.UserProblemRepository;
import com.math.weakness.service.UserService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class SaveProblemTest {

    SaveProblem saveProblem = new SaveProblem.SaveProblemUsecase(
            this.jwtService,
            this.userService,
            this.problemRepository,
            (ANY) -> System.out.println("updated")
    );
    @Test
    void saveOrUpdateResultTest() {
        final Long problemId = 1L;
        final String answer = "answer";
        final String token = "TOKEN";

        saveProblem.update(problemId, answer, token);
    }

ProblemRepository problemRepository = new ProblemRepository() {
    @Override
    public List<Long> findId(Integer difficulty, Boolean status, String subject) {
        return null;
    }

    @Override
    public Long findUserId(String accessToken) {
        return null;
    }

    @Override
    public List<Long> findId(Long id, Integer difficulty, Boolean status, String subject) {
        return null;
    }

    @Override
    public Problem findById(Long id) {
        return null;
    }
};

        JwtService jwtService = new JwtService(){
            @Override
            public Claims parseJwt(String jwt) {
                return null;
            }
        };
        UserService userService = new UserService(null,null) {
            @Override
            public Long findByEmail(String email) {
                return null;
            }
        };
}

