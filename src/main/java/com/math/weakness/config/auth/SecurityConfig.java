package com.math.weakness.config.auth;

import com.math.weakness.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .headers().frameOptions().disable()
                    .and()
                        .authorizeHttpRequests()
                        .antMatchers("/","/css/**","/js/**","/images/**","/problems").permitAll()
                        .antMatchers("/problems/add").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated()
                    .and()
                        .logout()
                        .logoutSuccessUrl("/")
                    .and()
                        .oauth2Login()
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService);
    }
}
