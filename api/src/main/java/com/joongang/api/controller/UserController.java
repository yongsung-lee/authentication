package com.joongang.api.controller;

import com.joongang.api.domain.User;
import com.joongang.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users")
    public List<User> all() {
        return userRepository.findAll();
    }

    @GetMapping("/api/user/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.getOne(id);
    }

}
