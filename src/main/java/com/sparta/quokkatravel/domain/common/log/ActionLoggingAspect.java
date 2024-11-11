package com.sparta.quokkatravel.domain.common.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ActionLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger("ActionLogger");

    // Coupon, Reservation, Payment
    @Around("execution(* com.sparta.quokkatravel.domain.coupon.service.*(..)) || " +
            "execution(* com.sparta.quokkatravel.domain.reservation.service.*(..)) || " +
            "execution(* com.sparta.quokkatravel.domain.payment.service.*(..))")
    public Object logUserActions(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Starting : {}", methodName);

        long startTime = System.currentTimeMillis();

        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("Exception in {} : {}", methodName, e.getMessage(), e);
            throw e;
        }

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Completed : {} in {} ms", methodName, duration);

        return result;
    }
}
