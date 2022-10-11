package com.itproject.petshome.filter;

import com.itproject.petshome.exception.UserNotFoundException;
import com.itproject.petshome.model.AdminDetail;
import com.itproject.petshome.service.AdminService;
import com.itproject.petshome.service.UserService;
import com.itproject.petshome.utils.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;
@Component
@AllArgsConstructor
public class AdminFilter  extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final AdminService adminService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();

        try {
            // Get user identity and set it on the spring security context
            AdminDetail adminDetail = adminService
                    .getUserDetailsByUsername(jwtTokenUtil.getUserNameFromToken(token));

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(
                    adminDetail, null,
                    adminDetail.getAuthorities()
            );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            // JWT not valid
        } catch (UsernameNotFoundException | UserNotFoundException e) {
            // User deleted
        }

        chain.doFilter(request, response);
    }
}
