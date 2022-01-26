package com.math.weakness.service;

import com.math.weakness.domain.User;
import com.math.weakness.repository.UserProblemRepository;
import com.math.weakness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProblemRepository userProblemRepository;

    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    public Long findByEmail(String email) {
       return userRepository.findByEmail(email)
               .orElseThrow()
               .getUserId();
    }
}
