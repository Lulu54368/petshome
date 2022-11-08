package com.itproject.petshome.config;

import com.itproject.petshome.filter.AdminFilter;
import com.itproject.petshome.filter.AuthoritiesLoggingAfterFilter;
import com.itproject.petshome.filter.CORSFilter;
import com.itproject.petshome.filter.JwtTokenFilter;
import com.itproject.petshome.service.AdminService;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
@Data
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ApplicationProperties properties;
    private final AdminServerProperties adminServer;
    private final AdminFilter adminFilter;
    private final SecurityProperties security;
    private final CORSFilter corsFilter;

    private final AuthoritiesLoggingAfterFilter authoritiesLoggingAfterFilter;
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;
    private final JwtTokenFilter jwtTokenFilter;
    //private final CorsFilter corsFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {




            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .cors().and().csrf().disable()
                    .addFilterAfter(jwtTokenFilter,
                            UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(adminFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(authoritiesLoggingAfterFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(corsFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests((auth) -> {
                                try {
                                    auth
                                            .antMatchers("/api/vi/user/*").authenticated()
                                            .antMatchers("/api/v1/admin/*").permitAll()

                                            .antMatchers("/api/vi/**").permitAll()
                                            .and()
                                            .rememberMe()
                                            .key(UUID.randomUUID().toString())
                                            .tokenValiditySeconds(1209600);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    )
                    .httpBasic(Customizer.withDefaults());
        http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                );

            return http.build();

        }



}
