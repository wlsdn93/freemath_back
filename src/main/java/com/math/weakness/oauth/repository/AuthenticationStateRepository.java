package com.math.weakness.oauth.repository;

import com.math.weakness.domain.AuthenticationState;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationStateRepository extends JpaRepository<AuthenticationState, Long> {

    void deleteByValidTimeAfter(LocalDateTime now);

}
