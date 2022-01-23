package com.math.weakness.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
public class PageResponse {

    private List<ProblemShow> content;

    //총 페이지 번호
    private long totalPage;
    //현재 페이지 번호
    private long page;
    //목록 사이즈
    private long size;
    //시작 페이지 번호, 끝 페이지 번호
    private long start, end;
    //이전, 다음
    private boolean first, last;
    //페이지 번호  목록
    private List<Integer> pageList;

    public PageResponse(Page<ProblemShow> problemList) {
        Pageable pageable = problemList.getPageable();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int totalPage = problemList.getTotalPages();
        int startPage = (pageNumber / pageSize) * pageSize + 1;
        int tempEndPage = startPage + pageSize - 1;
        int endPage = Math.min(tempEndPage, totalPage);
        List<Integer> pageList = new ArrayList<>();
        for ( int i = startPage ; i <= endPage ; i++) {
            pageList.add(i);
        }

        this.content = problemList.getContent();
        this.totalPage = totalPage;
        this.page = pageNumber + 1;
        this.size = pageSize;
        this.start = startPage;
        this.end = endPage;
        this.pageList = pageList;
    }
}
