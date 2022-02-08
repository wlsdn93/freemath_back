package com.math.weakness.repository;

import com.math.weakness.domain.UserProblem;
import com.math.weakness.domain.UserProblemId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProblemRepository extends JpaRepository<UserProblem, Long> {

    Optional<UserProblem> findByUserProblemId(UserProblemId userProblemId);

}
