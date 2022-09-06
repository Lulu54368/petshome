package com.itproject.petshome.config;

import com.itproject.petshome.filter.JwtTokenFilter;
import com.itproject.petshome.service.UserService;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import reactor.netty.http.client.HttpClient;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Order(1)
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {


    private final ApplicationProperties properties;
    private final AdminServerProperties adminServer;

    private final SecurityProperties security;
    private UserService userService;
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    private final PasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin")
                .password(passwordEncoder.encode("admin")).roles("ADMIN");
        auth.userDetailsService(userService::getUserDetailsByEmail);
    }
    @Bean
    public ClientHttpConnector customHttpClient() {
        SslContextBuilder sslContext = SslContextBuilder.forClient();
        //Your sslContext customizations go here
        HttpClient httpClient = HttpClient.create().secure(
                ssl -> ssl.sslContext(sslContext)
        );
        return new ReactorClientHttpConnector(httpClient);
    }





    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF

        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                .antMatchers("/api/v1/admin/**").permitAll();

       SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");

        http.addFilterAfter(new CustomFilter(),
                BasicAuthenticationFilter.class);

        http
                .authorizeRequests()

                .antMatchers("/swagger-ui/index.html").permitAll()
                .antMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
                .antMatchers(this.adminServer.getContextPath() + "/login").permitAll()
                .and()
                .formLogin()
                .loginPage(this.adminServer.getContextPath() + "/login")
                .successHandler(successHandler)
                .and()
                .logout()
                .logoutUrl(this.adminServer.getContextPath() + "/logout")
                .and()

                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);




        // Add JWT token filter



    }


    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(properties.getAllowedOrigins());
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}