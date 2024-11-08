package com.sparta.quokkatravel.domain.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Slf4j
@Component
public class RoomCacheAspect extends AbstractCacheAspect {

    private final RedissonClient redissonClient;

    protected RoomCacheAspect(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        super(redisTemplate);
        this.redissonClient = redissonClient;
    }

    @Before("@annotation(com.sparta.quokkatravel.domain.common.cache.InvalidateRoomCache)")
    public void evictRoomCache(JoinPoint joinPoint) {
        Long roomId = extractIdFromArgs(joinPoint);
        if (roomId == null) {
            log.warn("Room ID not found in method arguments. Cache was not invalidated.");
            return;
        }

        RLock lock = redissonClient.getLock("roomLock: " + roomId);
        try {
            if (lock.tryLock(5, 3, TimeUnit.SECONDS)) {
                evictCacheByPattern("Room::" + roomId + "*");
            } else {
                log.warn("Failed to acquire lock for Room cache eviction on Room Id: {}", roomId);
            }
        } catch (InterruptedException e) {
            log.error("Interrupted while trying to acquire lock for Room cache eviction on room ID: {}", roomId, e);
        } finally {
            lock.unlock();
        }

    }
}
