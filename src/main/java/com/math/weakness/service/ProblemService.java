package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.repository.SpringDataProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public Long addProblem(ProblemRequestDto params) {
        Problem entity = problemRepository.save(params.toEntity());
        return entity.getProblemId();
    }

    /**
     * 삭제
     */
    @Transactional
    public void deleteProblem(ProblemRequestDto params) {
        problemRepository.delete(params.toEntity());
    }

    @Transactional
    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    /**
     * 검색
     */



    /**
     * 문제 리스트 조회
     */
    public List<ProblemResponseDto> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "PROBLEM_ID");
        List<Problem> problems = problemRepository.findAll(sort);

        List<ProblemResponseDto> problemList = new ArrayList<>();
        for (ProblemResponseDto entity : problemList) {
            problemList.add(entity);
        }

        return problemList;
    }


    /**
     * 수정
     */
    @Transactional
    public Long problemUpdate(Long id, ProblemRequestDto params) {
        Problem entity = problemRepository.findById(id).get();
        entity.update(params.getTitle(), params.getAnswer(), params.getAuthor());
        return id;
    }


}
