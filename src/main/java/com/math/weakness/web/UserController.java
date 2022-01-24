package com.math.weakness.web;

import com.math.weakness.repository.UserRepository;
import com.math.weakness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

}
