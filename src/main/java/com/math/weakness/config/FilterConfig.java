package com.math.weakness.config;

import com.math.weakness.filter.AdminRequestFilter;
import com.math.weakness.filter.CorsFilter;
import com.math.weakness.filter.TokenValidationFilter;
import com.math.weakness.oauth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtService jwtService;

    @Bean
    public FilterRegistrationBean<CorsFilter> cors() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        registrationBean.setName("CorsFilter");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<TokenValidationFilter> isValidToken() {
        FilterRegistrationBean<TokenValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenValidationFilter(jwtService));
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.setOrder(2);
        registrationBean.setName("TokenValidationFilter");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AdminRequestFilter> isAdmin() {
        FilterRegistrationBean<AdminRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminRequestFilter(jwtService));
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setOrder(3);
        registrationBean.setName("AdminRequestFilter");
        return registrationBean;
    }
}
