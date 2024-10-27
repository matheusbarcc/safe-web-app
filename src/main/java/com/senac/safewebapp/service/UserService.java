package com.senac.safewebapp.service;

import com.senac.safewebapp.exception.SafeWebAppException;
import com.senac.safewebapp.model.entity.User;
import com.senac.safewebapp.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public User create(User user) {
        return repository.save(user);
    }

    public List<User> fetchAll() {
        return repository.findAll();
    }

    public User findUserById(String id) throws SafeWebAppException {
        return repository.findById(id).orElseThrow(() -> new SafeWebAppException("User not found"));
    }
}
