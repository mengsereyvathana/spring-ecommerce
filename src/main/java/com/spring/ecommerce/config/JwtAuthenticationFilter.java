package com.spring.ecommerce.config;

import com.spring.ecommerce.repository.TokenRepository;
import com.spring.ecommerce.service.JwtService;
import com.spring.ecommerce.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtService jwtService;

    private final UserService userService;

    private final TokenRepository tokenRepository;

    public static String CURRENT_USER = "";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        // hello

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If the access card (JWT) is missing or doesn't have the right format, allow the person to proceed.
            filterChain.doFilter(request, response);
            return;
        }
        // Extracting information from the access card (JWT).
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        CURRENT_USER = userEmail;

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

            // Fetching user details from the database.
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

            // Checking if the access card (JWT) is still valid and hasn't been revoked.
            boolean isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid){
                // Creating a temporary access card (authentication token) for the person to proceed.
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Adding additional details to the access card.
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token); //
                SecurityContextHolder.setContext(securityContext); //

                //SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
