package com.math.weakness.repository;

import com.math.weakness.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataProblemRepository extends JpaRepository<Problem, Long> {

}
