package com.math.weakness.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;
}
