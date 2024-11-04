package com.sparta.quokkatravel.domain.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class RoomCacheAspect extends AbstractCacheAspect {

    protected RoomCacheAspect(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    @Before("@annotation(InvalidateRoomCache)")
    public void evictRoomCache(JoinPoint joinPoint) {
        Long roomId = extractIdFromArgs(joinPoint);
        if (roomId == null) {
            log.warn("Room ID not found in method arguments. Cache was not invalidated.");
            return;
        }

        evictCacheByPattern("Room::" + roomId + "*");
    }
}
