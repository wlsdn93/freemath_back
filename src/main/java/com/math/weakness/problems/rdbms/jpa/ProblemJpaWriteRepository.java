package com.math.weakness.problems.rdbms.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemJpaWriteRepository extends JpaRepository<ProblemWriteEntity, Long> {

}
