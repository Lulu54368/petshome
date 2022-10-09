package com.itproject.petshome.config;

import com.itproject.petshome.filter.JwtTokenFilter;
import com.itproject.petshome.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.UUID;

@Configuration
@Order(2)
@EnableWebSecurity
@AllArgsConstructor
public class APITokenSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final JwtTokenFilter jwtTokenFilter;
    private final PasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {




    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{




    }
}
