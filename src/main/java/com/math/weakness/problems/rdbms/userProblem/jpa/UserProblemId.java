package com.math.weakness.problems.rdbms.userProblem.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProblemId implements Serializable {
    @Column
    private Long userId;

    @Column
    private Long problemId;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public UserProblemId(Long userId, Long problemId) {
        this.userId = userId;
        this.problemId = problemId;
    }
}
