package com.itproject.petshome.config;

import com.itproject.petshome.filter.AdminFilter;
import com.itproject.petshome.filter.JwtTokenFilter;
import com.itproject.petshome.service.AdminService;
import com.itproject.petshome.service.UserService;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import reactor.netty.http.client.HttpClient;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
@Data
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ApplicationProperties properties;
    private final AdminServerProperties adminServer;
    private final AdminFilter adminFilter;
    private final SecurityProperties security;



    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;
    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {




            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .cors().configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                            config.setAllowCredentials(true);
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setExposedHeaders(Arrays.asList("Authorization"));
                            config.setMaxAge(3600L);
                            return config;
                        }
                    }).and().csrf().disable()
                    .addFilterBefore(jwtTokenFilter,
                            UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(adminFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests((auth) -> {
                                try {
                                    auth
                                            //.antMatchers("/api/v1/admin/*").hasRole("ADMIN")

                                            .antMatchers("/api/vi/*").permitAll()
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
