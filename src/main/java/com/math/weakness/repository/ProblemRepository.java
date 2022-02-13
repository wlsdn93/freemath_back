package com.math.weakness.repository;

import com.math.weakness.domain.Problem;
import com.math.weakness.dto.ProblemShow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long>, CustomProblemRepository {

    @Override
    Page<ProblemShow> getPageForUser(Long id, Pageable pageable, Integer difficulty, Boolean status, String subject);

    @Override
    Page<ProblemShow> getPageForGuest(Pageable pageable, Integer difficulty, Boolean status, String subject);

    @Override
    List<Long> problemIdListForUser(Long id, Integer difficulty, Boolean status, String subject);

    @Override
    List<Long> problemIdListForGuest(Integer difficulty, Boolean status, String subject);
}
