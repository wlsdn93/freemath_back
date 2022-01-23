package com.math.weakness.repository;

import com.math.weakness.dto.ProblemShow;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.math.weakness.domain.QProblem.problem;
import static com.math.weakness.domain.QUserProblem.userProblem;
import static com.querydsl.core.types.Projections.fields;

@Repository
@RequiredArgsConstructor
public class CustomProblemRepositoryImpl implements CustomProblemRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemShow> findByDifficultyAndStatusAndId(Long id, Pageable pageable,
            Integer difficulty, Boolean status) {
        Conditions conditions = Conditions.of(id, pageable, difficulty, status);
        List<ProblemShow> content = this.query(conditions).fetch();
        return PageableExecutionUtils.getPage(content, pageable, this.query(conditions)::fetchCount);
    }

    @Override
    public Page<ProblemShow> findByDifficultyAndStatus(Pageable pageable, Integer difficulty,
            Boolean status) {
        List<ProblemShow> queryResult = jpaQueryFactory
                .select(Projections.fields(ProblemShow.class,
                        problem.problemId,
                        problem.title,
                        problem.difficulty))
                .from(problem)
                .where(this.isEqProblem(difficulty), this.isEqUserProblem(status))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(problem.problemId.desc())
                .fetch();

        JPAQuery<ProblemShow> count = jpaQueryFactory
                .select(Projections.fields(ProblemShow.class,
                        problem.problemId,
                        problem.title,
                        problem.difficulty))
                .from(problem)
                .where(this.isEqProblem(difficulty), this.isEqUserProblem(status))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(problem.problemId.desc());

        return PageableExecutionUtils.getPage(queryResult, pageable, () -> count.fetchCount());
    }



    private JPAQuery<ProblemShow> query(Conditions conditions) {
        return jpaQueryFactory
                .select(this.fieldProjection())
                .from(problem)
                .leftJoin(userProblem)
                .on(this.isEqProblemAndUserProblemId().and(this.isEqUserProblem(conditions.getId())))
                .where(this.isEqProblem(conditions.getDifficulty()), this.isEqUserProblem(conditions.getStatus()))
                .offset(conditions.getPageable().getOffset())
                .limit(conditions.getPageable().getPageSize())
                .orderBy(problem.problemId.desc());
    }

    private QBean<ProblemShow> fieldProjection() {
        return fields(ProblemShow.class,
                problem.problemId,
                problem.title,
                problem.difficulty,
                userProblem.status);
    }

    private BooleanExpression isEqProblemAndUserProblemId() {
        return problem.problemId.eq(userProblem.problem.problemId);
    }

    private BooleanExpression isEqProblem(Integer difficulty) {
        return difficulty == null ? null : problem.difficulty.eq(difficulty);
    }

    private BooleanExpression isEqUserProblem(Long id) {
        return userProblem.user.userId.eq(id);
    }

    private BooleanExpression isEqUserProblem(Boolean status) {
        return status == null ? null : userProblem.status.eq(status);
    }

    @Value(staticConstructor = "of")
    private static class Conditions {
        Long id;
        Pageable pageable;
        Integer difficulty;
        Boolean status;
    }
}