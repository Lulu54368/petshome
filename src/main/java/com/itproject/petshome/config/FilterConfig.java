package com.itproject.petshome.config;

import com.itproject.petshome.filter.AdminFilter;
import com.itproject.petshome.filter.JwtTokenFilter;
import com.itproject.petshome.service.AdminService;
import com.itproject.petshome.service.UserService;
import com.itproject.petshome.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@AllArgsConstructor
@Configuration
public class FilterConfig{
    private final JwtTokenUtil jwtTokenUtil;

    private final AdminService adminService;
    private final UserService userService;
    AutowireCapableBeanFactory beanFactory;
    @Bean
    public FilterRegistrationBean<AdminFilter> adminRegistrationBean() {
        FilterRegistrationBean <AdminFilter> registrationBean = new FilterRegistrationBean();
        AdminFilter adminFilter= new AdminFilter(jwtTokenUtil, adminService);
        beanFactory.autowireBean(adminFilter);
        registrationBean.setFilter(adminFilter);
        registrationBean.addUrlPatterns("/api/vi/auth/admin/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<JwtTokenFilter> userRegistrationBean() {
        FilterRegistrationBean <JwtTokenFilter> registrationBean = new FilterRegistrationBean();
        JwtTokenFilter userFilter= new JwtTokenFilter( jwtTokenUtil, userService);
        beanFactory.autowireBean(jwtTokenUtil);
        registrationBean.setFilter(userFilter);
        registrationBean.addUrlPatterns("/api/vi/auth/user/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
