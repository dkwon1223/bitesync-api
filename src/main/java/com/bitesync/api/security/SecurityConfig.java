package com.bitesync.api.security;

import com.bitesync.api.repository.UserRepository;
import com.bitesync.api.security.filter.AuthenticationFilter;
import com.bitesync.api.security.filter.CustomAuthenticationEntryPoint;
import com.bitesync.api.security.filter.ExceptionHandlerFilter;
import com.bitesync.api.security.filter.JWTAuthorizationFilter;
import com.bitesync.api.security.manager.CustomAuthenticationManager;
import com.bitesync.api.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private CustomAuthenticationManager customAuthenticationManager;
    private CustomCorsConfig customCorsConfig;
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private UserRepository userRepository;
    private UserServiceImpl userServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager, userRepository, userServiceImpl);
        authenticationFilter.setFilterProcessesUrl("/user/authenticate");
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(customCorsConfig))
                .headers((headers) -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers("/user/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling ->
                                       exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class);
        return http.build();
    }
}
