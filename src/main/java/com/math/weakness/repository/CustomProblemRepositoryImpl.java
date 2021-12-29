package com.math.weakness.repository;

import com.math.weakness.domain.Problem;
import com.math.weakness.domain.ProblemShow;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.math.weakness.domain.QProblem.problem;
import static com.math.weakness.domain.QUserProblem.userProblem;

@Repository
public class CustomProblemRepositoryImpl implements CustomProblemRepository{

    private final JPAQueryFactory jpaQueryFactory;
    public CustomProblemRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private long offset = 0;

    public void validateOffset(int offset) {
        if (offset < 0) { throw new IllegalArgumentException("Offset must not be negative"); }
    }

    @Override
    public Page<ProblemShow> SearchProblemsWithStatus(Long id, Pageable pageable) {

        if( pageable.getOffset() < 10 ) {
            offset = 10L;
        } else {
            offset = pageable.getOffset();
        }

        List<ProblemShow> queryResult = jpaQueryFactory
                .select(Projections.fields(ProblemShow.class,
                        problem.problemId,
                        problem.title,
                        problem.difficulty,
                        userProblem.status))
                .from(problem)
                .leftJoin(userProblem)
                .on(problem.problemId.eq(userProblem.problem.problemId)
                        .and(userProblem.user.userId.eq(id)))
                .offset(offset-10)
                .limit(pageable.getPageSize())
                .orderBy(problem.problemId.desc())
                .fetch();

        JPAQuery<ProblemShow> count = jpaQueryFactory
                .select(Projections.fields(ProblemShow.class,
                        problem.problemId,
                        problem.title,
                        problem.difficulty,
                        userProblem.status))
                .from(problem)
                .leftJoin(userProblem)
                .on(problem.problemId.eq(userProblem.problem.problemId)
                        .and(userProblem.user.userId.eq(id)))
                .orderBy(problem.problemId.desc());

         return PageableExecutionUtils.getPage(queryResult, pageable, () -> count.fetchCount());
    }
}
