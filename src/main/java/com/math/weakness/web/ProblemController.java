package com.math.weakness.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProblemController {

//    @Autowired
//    private final ProblemRepository problemRepository;

    @GetMapping("/problems")
    public String items(Model model) {
        return "problems";
    }

}
