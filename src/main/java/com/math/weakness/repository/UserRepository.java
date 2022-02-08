package com.math.weakness.repository;

import com.math.weakness.domain.Role;
import com.math.weakness.domain.User;
import com.math.weakness.domain.UserProblemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
