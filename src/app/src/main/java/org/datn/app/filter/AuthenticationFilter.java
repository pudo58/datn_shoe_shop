package org.datn.app.filter;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.User;
import org.datn.app.core.service.UserService;
import org.hibernate.annotations.Filter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Filter(name = "authenticationFilter")
@RequiredArgsConstructor
public class AuthenticationFilter  extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "User not authenticated");
            return;
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "User not found");
            return;
        }
        System.out.println(authentication.isAuthenticated() + " " + authentication.getName());
        filterChain.doFilter(request, response);
    }
}
