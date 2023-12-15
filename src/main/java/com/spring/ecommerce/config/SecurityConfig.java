package com.spring.ecommerce.config;

import com.spring.ecommerce.enums.Role;
import com.spring.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.spring.ecommerce.enums.Permission.*;
import static com.spring.ecommerce.enums.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // to use @PreAuthorize annotation
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/no-auth",
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                //who has access to different areas based on their roles
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(WHITE_LIST_URL)
                                .permitAll()

                                //User
                                .requestMatchers("/api/v1/user/**").hasRole(USER.name())
                                .requestMatchers(GET, "/api/v1/user/**").hasAuthority(USER_READ.name())
                                .requestMatchers(POST, "/api/v1/user/**").hasAuthority(USER_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/user/**").hasAuthority(USER_CREATE.name())
                                .requestMatchers(DELETE, "/api/v1/user/**").hasAuthority(USER_CREATE.name())

                                //Manager
                                .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                                .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

                                .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())


                                .anyRequest()
                                .authenticated()
                )
                // configure to be stateless, (no session state is stored on the server) user will always be checked
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Hiring a professional security team
                .authenticationProvider(authenticationProvider())
                // Adding Additional Security Layer (JWT Authentication Filter) (security guard)
                // before the standard username/password authentication.
                // It's like having an additional check at the entrance before guests can proceed
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Managing user checkout
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }


    // team of security personnel responsible for verifying the identity of guests (users). It uses a guest registry (user details service) to check if a guest is legitimate
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    //It ensures that user passwords are securely stored and compared during the authentication process
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //The manager overseeing the entire security system. It ensures that the security team (authentication providers) and the key card system (password encoder) work together seamlessly
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
