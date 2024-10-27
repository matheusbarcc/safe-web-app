package com.senac.safewebapp.controller;

import com.senac.safewebapp.model.entity.User;
import com.senac.safewebapp.auth.AuthService;
import com.senac.safewebapp.model.enums.Role;
import com.senac.safewebapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("authenticate")
    public String authenticate(Authentication authentication) {
        return authService.authenticate(authentication);
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {

        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        if(user.getRole().equals(Role.ADMIN)) {
            user.setRole(Role.ADMIN);
        }

        return userService.create(user);
    }
}
