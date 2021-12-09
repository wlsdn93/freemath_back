package com.math.weakness.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class Problem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    @Column(name = "IMAGE_ADDRESS")
    private String imageAddress;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "DIFFICULTY")
    private Integer difficulty;
}
