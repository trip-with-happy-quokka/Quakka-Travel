package com.sparta.quokkatravel.domain.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Slf4j
@Component
public class CacheAspect {

    private final RedisTemplate<String, Object> redisTemplate;

    public CacheAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @AfterReturning("@annotation(InvalidateAccommodationCache)")
    public void evictAccommodationCache(JoinPoint joinPoint) {
        Long accommodationId = null;

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Long) {
                accommodationId = (Long) arg;
                break;
            }
        }


        try {

            if (accommodationId != null) {
                String cachePattern = "Accommodation::" + accommodationId;
                Set<String> keys = redisTemplate.keys(cachePattern);

                if (keys != null && !keys.isEmpty()) {
                    redisTemplate.delete(keys);
                    log.info("Cache: " + cachePattern + " Cache is deleted");
                }

            } else {
                log.warn("Accommodation ID not found in method arguments. Cache was not invalidated.");
            }

        } catch (Exception e) {
            log.error("Failed to invalidate cache for Accommodation ID: " + accommodationId, e);
        }


    }

    @AfterReturning("@annotation(InvalidateRoomCache)")
    public void evictRoomCache(JoinPoint joinPoint) {
        Long roomId = null;

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Long) {
                roomId = (Long) arg;
                break;
            }
        }

        try {

            if (roomId != null) {
                String cachePattern = "Room::" + roomId;
                Set<String> keys = redisTemplate.keys(cachePattern);

                if (keys != null && !keys.isEmpty()) {
                    redisTemplate.delete(keys);
                    log.info("Cache: " + cachePattern + " Cache is deleted");
                }
            } else {
                log.warn("Accommodation ID not found in method arguments. Cache was not invalidated.");
            }

        } catch (Exception e) {
            log.error("Failed to invalidate cache for Room: " + roomId, e);
        }


    }
}
