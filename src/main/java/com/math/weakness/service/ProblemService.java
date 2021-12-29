package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.domain.ProblemShow;
import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.repository.CustomProblemRepositoryImpl;
import com.math.weakness.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class ProblemService{

    private final ProblemRepository problemRepository;
    private final CustomProblemRepositoryImpl customProblemRepository;

    @Autowired
    public ProblemService(ProblemRepository problemRepository, CustomProblemRepositoryImpl customProblemRepository) {
        this.problemRepository = problemRepository;
        this.customProblemRepository = customProblemRepository;
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
     * 페이징 처리
     */
//    @org.springframework.transaction.annotation.Transactional(readOnly = true)
//    public Page<ProblemResponseDto> findAllProblem(Pageable pageable) {
//        int page = (pageable.getPageNumber() == 0 ) ? 0 : (pageable.getPageNumber()-1);
//        Sort sort = Sort.by(Sort.Direction.DESC, "problemId");
//        PageRequest pageRequest = PageRequest.of(page, 10, sort);
//        Page<Problem> problems = problemRepository.findAll(pageRequest);
//        Page<ProblemResponseDto> problemList = problems.map(problem -> new ProblemResponseDto(problem));
//        return problemList;
//    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Page<ProblemShow> showAllProblemsByUser(Pageable pageable, Long id) {
        Page<ProblemShow> problemList = problemRepository.SearchProblemsWithStatus(id, pageable);
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
