package com.itproject.petshome.config;

import com.itproject.petshome.filter.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CustomFilter> loggingFilter(){
        FilterRegistrationBean<CustomFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/api/v1/admin/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
