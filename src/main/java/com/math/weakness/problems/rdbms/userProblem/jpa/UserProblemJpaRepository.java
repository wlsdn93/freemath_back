package com.math.weakness.problems.rdbms.userProblem.jpa;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserProblemJpaRepository extends JpaRepository<UserProblem, UserProblemId> {
}
