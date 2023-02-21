package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.domain.User;
import com.math.weakness.domain.UserProblem;
import com.math.weakness.domain.UserProblemId;
import com.math.weakness.dto.Form;
import com.math.weakness.dto.PageResponse;
import com.math.weakness.oauth.service.JwtService;
import com.math.weakness.repository.ProblemRepository;
import com.math.weakness.repository.UserProblemRepository;
import io.jsonwebtoken.Claims;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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

    @Transactional(readOnly = true)
    public List<Long> getProblemIdList(String accessToken, Integer difficulty, String subject,
        Boolean status) {
        if (accessToken.equals("guest")) {
            return problemRepository.problemIdListForGuest(difficulty, status, subject);
        }
        Long id = this.getUserId(accessToken);
        return problemRepository.problemIdListForUser(id, difficulty, status, subject);
    }

    @Transactional(readOnly = true)
    public PageResponse showAllProblemsForUser(
        String accessToken,
        Pageable pageable,
        Integer difficulty,
        String subject,
        Boolean status
    ) {
        if (accessToken.equals("guest")) {
            return new PageResponse(
                problemRepository.getPageForGuest(pageable, difficulty, status, subject));
        }
        Long id = this.getUserId(accessToken);
        return new PageResponse(problemRepository
            .getPageForUser(id, pageable, difficulty, status, subject));
    }

    public Resource getProblemImage(Long problemId) {
        Problem problem = problemRepository.findById(problemId).orElseThrow();
        String problemImagePath = fileDir + problem.getProblemImageName();
        return new FileSystemResource(problemImagePath);
    }

    public Resource getSolutionImage(Long problemId) {
        Problem problem = problemRepository.findById(problemId).orElseThrow();
        String solutionImagePath = fileDir + problem.getSolutionImageName();
        return new FileSystemResource(solutionImagePath);
    }

    public boolean saveOrUpdateResult(Long problemId, String answer, String accessToken) {
        Problem problem = problemRepository.findById(problemId).orElseThrow();
        User user = userService.findById(this.getUserId(accessToken));
        boolean isCorrect = problem.getAnswer().equals(answer);
        UserProblemId userProblemId = new UserProblemId(this.getUserId(accessToken), problemId);
        userProblemRepository.findByUserProblemId(userProblemId)
            .ifPresentOrElse(
                isNotEmpty -> isNotEmpty.update(isCorrect, answer),
                () -> userProblemRepository.save(UserProblem.builder()
                    .user(user)
                    .problem(problem)
                    .status(isCorrect)
                    .submittedAnswer(answer)
                    .build()));
        return isCorrect;
    }

    public Long addProblem(Form formData) {
        this.storeImage(formData);
        return problemRepository.save(formData.toEntity())
            .getProblemId();
    }

    public void updateProblem(Form formData, Long problemId) {
        Problem problem = problemRepository.findById(problemId).get();
        problem.update(formData);
        try {
            this.storeImage(formData);
        } catch (Exception e) {
            log.error("fail to store image", e);
        }
    }

    public void deleteProblem(Long id) {
        problemRepository.deleteById(id);
    }

    public Form findById(Long id) {
        Problem foundProblem = problemRepository.findById(id)
            .orElseThrow();
        return Form.from(foundProblem);
    }

    private void storeImage(Form formData) {
        String[] problemString = formData.getProblemImage().split(",");
        String[] solutionString = formData.getSolutionImage().split(",");
        String problemImagePath = fileDir + formData.getProblemImageName();
        String solutionImagePath = fileDir + formData.getSolutionImageName();

        byte[] problemByte = Base64.getDecoder().decode(problemString[1]);
        byte[] solutionByte = Base64.getDecoder().decode(solutionString[1]);

        try (OutputStream outputStream = new BufferedOutputStream(
            new FileOutputStream(problemImagePath))) {
            outputStream.write(problemByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (OutputStream outputStream = new BufferedOutputStream(
            new FileOutputStream(solutionImagePath))) {
            outputStream.write(solutionByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long getUserId(String accessToken) {
        Claims claims = jwtService.parseJwt(accessToken);
        String email = claims.get("email").toString();
        return userService.findByEmail(email);
    }

}
