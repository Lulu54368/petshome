package com.itproject.petshome.service;


import com.itproject.petshome.model.User;
import com.itproject.petshome.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService {
    private final UserRepository userRepository;

    public Optional<User> getCurrentUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String email = auth.getName();
        return userRepository.findByEmail(email);
    }

    public User getCurrentUserOrThrow() {
        return getCurrentUser().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }



}
