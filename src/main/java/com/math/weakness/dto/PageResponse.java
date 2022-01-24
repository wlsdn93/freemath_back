package com.math.weakness.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class PageResponse {

    private final List<ProblemShow> content;
    private final long totalPage;
    private final long page;
    private final long size;
    private final long start, end;
    private boolean first;
    private boolean last;
    private final List<Integer> pageList;

    public PageResponse(Page<ProblemShow> problemList) {
        Pageable pageable = problemList.getPageable();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int totalPage = problemList.getTotalPages();
        int startPage = (pageNumber / pageSize) * pageSize + 1;
        int tempEndPage = startPage + pageSize - 1;
        int endPage = Math.min(tempEndPage, totalPage);

        this.content = problemList.getContent();
        this.totalPage = totalPage;
        this.page = pageNumber + 1;
        this.size = pageSize;
        this.start = startPage;
        this.end = endPage;
        this.pageList = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toUnmodifiableList());
    }
}
