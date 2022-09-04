package com.itproject.petshome.config;

import io.jsonwebtoken.io.IOException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomFilter extends GenericFilterBean {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws  ServletException, java.io.IOException {
        chain.doFilter(request, response);
    }
}
