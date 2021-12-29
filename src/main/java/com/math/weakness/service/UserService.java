package com.math.weakness.service;

import com.math.weakness.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long findByEmail(String email) {
       return userRepository.findByEmail(email).get().getUserId();
    }
}
