package com.math.weakness.repository;

import com.math.weakness.dto.ProblemShow;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface CustomProblemRepository {

    @Transactional(readOnly = true)
    Page<ProblemShow> getPageForUser(Long id, Pageable pageable, Integer difficulty, Boolean status, String subject);

    @Transactional(readOnly = true)
    Page<ProblemShow> getPageForGuest(Pageable pageable, Integer difficulty, Boolean status, String subject);

    @Transactional(readOnly = true)
    List<Long> problemIdListForUser(Long id, Integer difficulty, Boolean status, String subject);

    @Transactional(readOnly = true)
    List<Long> problemIdListForGuest(Integer difficulty, Boolean status, String subject);

}
