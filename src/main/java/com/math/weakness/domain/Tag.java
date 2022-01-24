package com.math.weakness.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_name")
    private String tagName;

    @OneToMany(mappedBy = "tag",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProblemTag> problemTags = new ArrayList<>();

}
