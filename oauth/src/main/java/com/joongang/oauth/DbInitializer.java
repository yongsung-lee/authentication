package com.joongang.oauth;

import com.joongang.oauth.domain.User;
import com.joongang.oauth.domain.UserRole;
import com.joongang.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!userService.existByName("branduser")) {
            User user = new User();
            user.setName("branduser");
            user.setPassword(passwordEncoder.encode("1234"));
            user.getRoles().add(UserRole.ADMIN);

            userService.save(user);
        }
    }
}
