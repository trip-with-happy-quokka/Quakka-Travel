package com.sparta.quokkatravel.domain.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@Slf4j
public abstract class AbstractCacheAspect {

    protected final RedisTemplate<String, Object> redisTemplate;

    protected AbstractCacheAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    // 캐시 패턴에 따른 삭제 메서드
    protected void evictCacheByPattern(String cachePattern) {
        try {
            Set<String> keys = redisTemplate.keys(cachePattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.info("Cache deleted for pattern: {}", cachePattern);
            } else {
                log.info("No cache found for pattern: {}", cachePattern);
            }
        } catch (Exception e) {
            log.error("Failed to invalidate cache for pattern: " + cachePattern, e);
        }
    }

    // 메서드 인자에서 Long ID 추출
    protected Long extractIdFromArgs(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Long) {
                return (Long) arg;
            }
        }
        return null;
    }
}
