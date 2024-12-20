package com.sparta.quokkatravel.domain.common.config;

import com.sparta.quokkatravel.domain.common.jwt.CustomAuthenticationSuccessHandler;
import com.sparta.quokkatravel.domain.common.jwt.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (JWT 사용 시 필요하지 않음)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않음 (JWT 기반 인증)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("/v3/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api/v1/users", "/api/v1/users/login").permitAll() // 인증 관련 엔드포인트는 모두 접근 가능
                        .requestMatchers("/api/v1/notify/").hasRole("ADMIN")
                        .requestMatchers("/topic/notifications/").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/index.html/**").permitAll()
                        .requestMatchers("/chat.html/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        .requestMatchers("/api/v1/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/payment/**").permitAll()
                        .requestMatchers("/Payment.html").permitAll()
                        .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
                .exceptionHandling(e -> e
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                                })
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 필터 추가
                .build();
    }
}
