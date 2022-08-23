package com.itproject.petshome.service;

import com.itproject.petshome.exception.EmailNotFoundException;
import com.itproject.petshome.model.UserDetails;
import com.itproject.petshome.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.lang.String.format;
@Service
@Data
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    public UserDetails getUserDetailsByEmail(String email) throws EmailNotFoundException {
        return UserDetails.of(userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new EmailNotFoundException()
                        )
                );
    }





}
