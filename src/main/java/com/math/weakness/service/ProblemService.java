package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.domain.UserProblem;
import com.math.weakness.dto.Form;
import com.math.weakness.dto.PageResponse;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.dto.UserProblemDto;
import com.math.weakness.oauth.service.JwtService;
import com.math.weakness.repository.ProblemRepository;
import com.math.weakness.repository.UserProblemRepository;
import io.jsonwebtoken.Claims;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final UserService userService;
    private final UserProblemRepository userProblemRepository;
    private final JwtService jwtService;

    @Value("${file.dir}")
    private String fileDir;

    public void saveResult(UserProblemDto userProblemDto) {
        Problem problem = problemRepository.findById(userProblemDto.getProblemId())
                .orElseThrow();

        String answer = userProblemDto.getAnswer();
        String problemAnswer = problem.getAnswer();

        log.info("problemAnswer = {}", problemAnswer);
        log.info("answer = {}", answer);

        userProblemRepository.save(UserProblem.builder()
                .user(userService.findById(userProblemDto.getUserId()))
                .problem(problem)
                .status(problemAnswer.equals(answer))
                .build());
    }

    public Long addProblem(Form formData) {
       
        this.storeImage(formData);
        return problemRepository.save(formData.toEntity())
                .getProblemId();
    }

    private void storeImage(Form formData) {
        String[] problemString = formData.getProblemImage().split(",");
        String problemImageName = formData.getProblemImageName();
        String[] solutionString = formData.getSolutionImage().split(",");
        String solutionImageName = formData.getSolutionImageName();

        byte[] problemByte = Base64.getDecoder().decode(problemString[1]);
        byte[] solutionByre = Base64.getDecoder().decode(solutionString[1]);

        String problemImagePath = fileDir + problemImageName;
        String solutionImagePath = fileDir + solutionImageName;
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(problemImagePath))) {
            outputStream.write(problemByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(solutionImagePath))) {
            outputStream.write(solutionByre);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    public ProblemResponseDto findById(Long id) {
        Problem foundProblem = problemRepository.findById(id)
                .orElseThrow();

        return ProblemResponseDto.from(foundProblem);
    }

    @Transactional(readOnly = true)
    public PageResponse showAllProblemsForUser(
            String accessToken,
            Pageable pageable,
            Integer difficulty,
            Boolean status
    ) {
        if (accessToken.equals("guest")) {
            return new PageResponse(problemRepository.findByDifficultyAndStatus(pageable, difficulty, status));
        }
        Claims claims = jwtService.parseJwt(accessToken);
        String email = claims.get("email").toString();
        Long id = userService.findByEmail(email);
        return new PageResponse(problemRepository
                .findByDifficultyAndStatusAndId(id, pageable, difficulty, status));
   }

}
