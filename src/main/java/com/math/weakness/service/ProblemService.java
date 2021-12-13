package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.repository.SpringDataProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    public void deleteProblem(ProblemRequestDto params) {
        problemRepository.delete(params.toEntity());
    }

    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    /**
     * 검색
     */



    /**
     * 문제 리스트 조회
     */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<ProblemResponseDto> findAll() {

        List<Problem> problems = problemRepository.findAll();
        List<ProblemResponseDto> problemList = new ArrayList<>();
        for (Problem entity : problems) {
            ProblemResponseDto responseDto = new ProblemResponseDto(entity);
            problemList.add(responseDto);
        }

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
