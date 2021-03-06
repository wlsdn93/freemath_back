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
    public Page<ProblemShow> getPageForUser(Long id, Pageable pageable, Integer difficulty, Boolean status, String subject) {
        Conditions conditions = Conditions.of(id, pageable, difficulty, status, subject);
        List<ProblemShow> content = this.query(conditions).fetch();
        return PageableExecutionUtils.getPage(content, pageable, this.query(conditions)::fetchCount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProblemShow> getPageForGuest(Pageable pageable, Integer difficulty, Boolean status, String subject) {
        List<ProblemShow> queryResult = jpaQueryFactory
                .select(Projections.fields(ProblemShow.class,
                        problem.problemId,
                        problem.title,
                        problem.difficulty,
                        problem.subject))
                .from(problem)
                .where(this.isEqProblem(difficulty), this.isEqProblem(subject))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(problem.problemId.desc())
                .fetch();

        JPAQuery<ProblemShow> count = jpaQueryFactory
                .select(Projections.fields(ProblemShow.class,
                        problem.problemId,
                        problem.title,
                        problem.difficulty,
                        problem.subject))
                .from(problem)
                .where(this.isEqProblem(difficulty), this.isEqProblem(subject))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(problem.problemId.desc());

        return PageableExecutionUtils.getPage(queryResult, pageable, count::fetchCount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> problemIdListForUser(Long id, Integer difficulty, Boolean status, String subject) {
        return jpaQueryFactory.select(problem.problemId)
                .from(problem)
                .leftJoin(userProblem)
                .where(this.isEqProblem(difficulty),
                        this.isEqProblem(subject),
                        this.isEqUserProblem(status))
                .on(this.isEqProblemAndUserProblemId().and(this.isEqUserId(id)))
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> problemIdListForGuest(Integer difficulty, Boolean status, String subject) {
        return jpaQueryFactory.select(problem.problemId)
                .from(problem)
                .where(this.isEqProblem(difficulty), this.isEqProblem(subject))
                .fetch();
    }

    private JPAQuery<ProblemShow> query(Conditions conditions) {
        return jpaQueryFactory
                .select(this.fieldProjection())
                .from(problem)
                .leftJoin(userProblem)
                .on(this.isEqProblemAndUserProblemId().and(this.isEqUserId(conditions.getId())))
                .where(this.isEqProblem(conditions.getDifficulty()),
                        this.isEqProblem(conditions.getSubject()),
                        this.isEqUserProblem(conditions.getStatus()))
                .offset(conditions.getPageable().getOffset())
                .limit(conditions.getPageable().getPageSize())
                .orderBy(problem.problemId.desc());
    }

    private QBean<ProblemShow> fieldProjection() {
        return fields(ProblemShow.class,
                problem.problemId,
                problem.title,
                problem.difficulty,
                problem.subject,
                userProblem.status);
    }

    private BooleanExpression isEqProblemAndUserProblemId() {
        return problem.problemId.eq(userProblem.problem.problemId);
    }

    private BooleanExpression isEqProblem(Integer difficulty) {
        return difficulty == null ? null : problem.difficulty.eq(difficulty);
    }

    private BooleanExpression isEqProblem(String subject) {
        return subject.isBlank()? null : problem.subject.eq(subject);
    }

    private BooleanExpression isEqUserProblem(Boolean status) {
        return status == null ? null : userProblem.status.eq(status);
    }

    private BooleanExpression isEqUserId(Long id) {
        return userProblem.user.userId.eq(id);
    }

    @Value(staticConstructor = "of")
    private static class Conditions {
        Long id;
        Pageable pageable;
        Integer difficulty;
        Boolean status;
        String subject;
    }
}
