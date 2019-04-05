package com.joongang.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/open-api/hello")
    public String getHello() {
        return "hello";
    }

}
