package com.math.weakness.repository;

import com.math.weakness.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<User, Long> {


}
