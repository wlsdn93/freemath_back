package com.math.weakness.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProblem {

    @EmbeddedId
    private UserProblemId userProblemId = new UserProblemId();

    @MapsId("userId")
    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @MapsId("problemId")
    @JoinColumn(name = "PROBLEM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "SUBMITTED_ANSWER")
    private String submittedAnswer;

    @Builder
    private UserProblem(User user, Problem problem, Boolean status, String submittedAnswer) {
        this.user = user;
        this.problem = problem;
        this.status = status;
        this.submittedAnswer = submittedAnswer;
    }

    public UserProblem update(Boolean status, String submittedAnswer) {
        this.status = status;
        this.submittedAnswer = submittedAnswer;
        return this;
    }
}
