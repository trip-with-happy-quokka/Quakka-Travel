package com.sparta.quokkatravel.domain.common.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ActionLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger("ActionLogger");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        logger.info("Starting : {} {}", httpMethod, requestURI);

        try {
            filterChain.doFilter(request, response);
        } finally {
            // 응답 정보 로깅
            long duration = System.currentTimeMillis() - startTime;
            int status = response.getStatus();
            logger.info("Completed : {} {} : {} in {} ms", httpMethod, requestURI, status, duration);
        }

    }
}
