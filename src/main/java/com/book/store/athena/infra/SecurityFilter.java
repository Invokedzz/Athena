package com.book.store.athena.infra;

import com.book.store.athena.model.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@SuppressWarnings("NullableProblems")
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenAuthService tokenAuthService;

    private final UserRepository userRepository;

    public SecurityFilter(TokenAuthService tokenAuthService, UserRepository userRepository) {

        this.tokenAuthService = tokenAuthService;

        this.userRepository = userRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = filterToken(request);

        if (token != null) {

            var subject = tokenAuthService.validateJWToken(token);

            var user = userRepository.findUserByName(subject);

            var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);

        }

        filterChain.doFilter(request, response);

    }

    private String filterToken (HttpServletRequest request) {

        String tokenSession = request.getHeader("Authorization");

        if (tokenSession != null && tokenSession.startsWith("Bearer ")) {

            return tokenSession.replace("Bearer ", "");

        }

        return null;

    }


}
