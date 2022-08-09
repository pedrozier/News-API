package com.standard.newsAPI.security.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.standard.newsAPI.security.services.*;

@Configuration
public class WebSecurityConfiguration {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager)
                        throws Exception {

                http

                                .csrf().disable()
                                .authorizeRequests()
                                .antMatchers("/login").permitAll()
                                .antMatchers(HttpMethod.GET, "/**").permitAll()
                                .antMatchers(HttpMethod.POST, "/**").authenticated()
                                .antMatchers(HttpMethod.DELETE, "/**").authenticated()
                                .antMatchers(HttpMethod.PUT, "/**").authenticated()
                                .and()
                                .addFilterBefore(new JwtLoginFilter("/login", authenticationManager),
                                                UsernamePasswordAuthenticationFilter.class)
                                .addFilterBefore(new JwtAuthenticationFilter(),
                                                UsernamePasswordAuthenticationFilter.class)
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                return http.getOrBuild();
        }

        @Bean
        public AuthenticationManager configure(HttpSecurity http) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder.inMemoryAuthentication()
                                .withUser("admin")
                                .password(passwordEncoder().encode("password"))
                                .roles("ADMIN");
                return authenticationManagerBuilder.getOrBuild();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

}
