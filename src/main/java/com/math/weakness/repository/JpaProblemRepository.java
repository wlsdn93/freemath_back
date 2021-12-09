package com.math.weakness.repository;

import com.math.weakness.domain.Problem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaProblemRepository implements ProblemRepository{

    private final EntityManager em;

    public JpaProblemRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Problem save(Problem problem) {
        em.persist(problem);
        return problem;
    }

    @Override
    public Optional<Problem> findById(Long id) {
        Problem problem = em.find(Problem.class, id);
        return Optional.ofNullable(problem);
    }

    @Override
    public List<Problem> findAll() {
        return em.createQuery("SELECT p FROM Problem p", Problem.class)
                .getResultList();
    }
}
