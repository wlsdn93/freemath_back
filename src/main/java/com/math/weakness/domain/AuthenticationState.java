package com.math.weakness.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class AuthenticationState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATE_ID")
    private Long stateId;

    @Column(name = "STATE")
    private String state;

    @Column(name = "VALID_TIME")
    private LocalDateTime validTime = LocalDateTime.now().plusSeconds(30);

    public AuthenticationState(String state) {
        this.state = state;
    }
}
