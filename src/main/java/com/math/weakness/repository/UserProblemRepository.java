package com.math.weakness.repository;

import com.math.weakness.domain.UserProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProblemRepository extends JpaRepository<UserProblem, Long> {
}
