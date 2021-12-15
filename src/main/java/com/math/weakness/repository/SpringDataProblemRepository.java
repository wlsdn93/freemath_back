package com.math.weakness.repository;

import com.math.weakness.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProblemRepository extends JpaRepository<Problem, Long> {

}
