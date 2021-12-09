package com.math.weakness.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Post {
    private Long postId;
    private String title;
    private String content;
    private String author;
    private Date date;
}
