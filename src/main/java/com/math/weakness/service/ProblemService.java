package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.repository.SpringDataProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ProblemService{

    private final SpringDataProblemRepository problemRepository;

    @Autowired
    public ProblemService(SpringDataProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    /**
     * 추가
     */
    public Long addProblem(ProblemRequestDto params) {
        Problem entity = problemRepository.save(params.toEntity());
        return entity.getProblemId();
    }

    /**
     * 삭제
     */
    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    /**
     * 문제 조회
     */
    public ProblemResponseDto findById(Long id) {
        Problem foundProblem = problemRepository.findById(id).get();
        ProblemResponseDto problemResponseDto = new ProblemResponseDto(foundProblem);
        return problemResponseDto;
    }


    /**
     * 문제 리스트 조회
     */
//    @org.springframework.transaction.annotation.Transactional(readOnly = true)
//    public List<ProblemResponseDto> findAll() {
//        Sort sort = Sort.by(Sort.Direction.DESC, "problemId");
//        List<Problem> problems = problemRepository.findAll(sort);
//        List<ProblemResponseDto> problemList = new ArrayList<>();
//        for (Problem entity : problems) {
//            ProblemResponseDto responseDto = new ProblemResponseDto(entity);
//            problemList.add(responseDto);
//        }
//
//        return problemList;
//    }

    /**
     * 페이징 처리
     */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Page<ProblemResponseDto> findAllProblem(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0 ) ? 0 : (pageable.getPageNumber()-1);
        Sort sort = Sort.by(Sort.Direction.DESC, "problemId");
        PageRequest pageRequest = PageRequest.of(page, 10, sort);
        Page<Problem> problems = problemRepository.findAll(pageRequest);
        Page<ProblemResponseDto> problemList = problems.map(problem -> new ProblemResponseDto(problem));
        return problemList;
    }


    /**
     * 수정
     */
    public Long problemUpdate(Long id, ProblemRequestDto params) {
        Problem entity = problemRepository.findById(id).get();
        entity.update(params.getTitle(), params.getAnswer(), params.getAuthor());
        return id;
    }


}
