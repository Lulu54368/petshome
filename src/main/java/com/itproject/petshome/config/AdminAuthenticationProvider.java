package com.itproject.petshome.config;

import com.itproject.petshome.exception.AdminNotFound;
import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.Admin;
import com.itproject.petshome.model.User;
import com.itproject.petshome.repository.AdminRepository;
import com.itproject.petshome.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminAuthenticationProvider implements AuthenticationProvider {
    private AdminRepository adminRepository;


    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        Admin admin = null;

        admin = adminRepository.findByUsername(username).orElseThrow(AdminNotFound::new);


        if (passwordEncoder.matches(pwd, admin.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, pwd);
        } else {
            throw new BadCredentialsException("Invalid password!");
        }

    }
    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
