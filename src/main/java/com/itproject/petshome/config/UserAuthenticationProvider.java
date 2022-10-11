package com.itproject.petshome.config;

import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.User;
import com.itproject.petshome.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {


    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        User user = null;

        user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);


        if (passwordEncoder.matches(pwd, user.getPassword())) {
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

