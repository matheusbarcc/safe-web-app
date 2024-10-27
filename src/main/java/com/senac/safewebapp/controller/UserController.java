package com.senac.safewebapp.controller;

import com.senac.safewebapp.exception.SafeWebAppException;
import com.senac.safewebapp.model.entity.User;
import com.senac.safewebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/protected/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/all")
    public List<User> fetchAll(Authentication authentication) throws SafeWebAppException {
        Jwt jwt = (Jwt) authentication.getPrincipal();

        String role = (String) jwt.getClaims().get("roles");

        if (role.equalsIgnoreCase("admin")) {
            return userService.fetchAll();
        } else {
            throw new SafeWebAppException("Unauthorized user");
        }
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return userService.findUserById(id);
    }
}
