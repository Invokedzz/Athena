package com.book.store.athena.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter filterChain;

    public SecurityConfig(SecurityFilter filterChain) {

        this.filterChain = filterChain;

    }

    @Bean
    protected SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable)
                                        .sessionManagement(custom ->

                                                custom.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                        .authorizeHttpRequests(e ->
                                        e.requestMatchers(HttpMethod.POST, "/users/register").permitAll())

                                        .authorizeHttpRequests(e ->
                                        e.requestMatchers(HttpMethod.POST, "/users/login").permitAll())

                                        .authorizeHttpRequests(e ->
                                                e.requestMatchers("/users/profile/{id}",
                                                        "/users/profile/books/{id}", "/users/profile/disable/{id}",
                                                                "/books/create", "/users/profile/reactivate/{id}",
                                                        "/books/collection", "/favorites/insert").hasRole("USER"))

                                        .authorizeHttpRequests(e ->
                                        e.requestMatchers(HttpMethod.POST, "/admin/login").permitAll())
                                        .authorizeHttpRequests(req ->
                                                req.requestMatchers("/admin/**",
                                                        "/users/actives", "/books/delete/{id}",
                                                        "/books/reactivate/{id}", "/favorites/display").hasRole("ADMIN"))

                                        .addFilterBefore(filterChain, UsernamePasswordAuthenticationFilter.class)
                                        .build();

    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

}
