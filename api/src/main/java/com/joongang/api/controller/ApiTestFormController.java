package com.joongang.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiTestFormController {

    @GetMapping("/api-test-form")
    public String apiTestForm() {
        return "api-test-form";
    }
}
