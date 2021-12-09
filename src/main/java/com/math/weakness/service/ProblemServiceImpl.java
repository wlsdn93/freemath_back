package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;


@Component
@Transactional
public class ProblemServiceImpl implements ProblemService{

    private final ProblemRepository problemRepository;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Override
    public Problem addProblem(Problem problem) {
        return null;
    }
}
