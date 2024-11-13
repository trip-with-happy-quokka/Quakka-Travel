package com.sparta.quokkatravel.domain.common.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmailAspect {

    @After("execution(* com.sparta.quokkatravel.domain.email.service.EmailSendService.sendSimpleMessageAsync(..))")
    public void afterSendReservationConfirmation(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String email = (String) args[0];
        System.out.println("Email sent to: " + email);  // 이메일 전송 후 로그
    }
}