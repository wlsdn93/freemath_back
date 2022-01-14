package com.math.weakness.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<UserProblem> userProblems = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    @Builder
    public User(String name, String email, Role role, Platform platform) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.platform = platform;
    }

    public User update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
