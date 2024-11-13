package com.sparta.quokkatravel.domain.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.concurrent.TimeUnit;

@Aspect
@Slf4j
@Component
public class MonthlyStatisticsCacheAspect extends AbstractCacheAspect {

    private final RedissonClient redissonClient;

    public MonthlyStatisticsCacheAspect(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        super(redisTemplate);
        this.redissonClient = redissonClient;
    }

    @Before("@annotation(invalidateMonthlyStatisticsCache)")
    public void evictMonthlyStatisticsCache(JoinPoint joinPoint, InvalidateMonthlyStatisticsCache invalidateMonthlyStatisticsCache) {
        YearMonth yearMonth = extractYearMonthFromArgs(joinPoint);
        if (yearMonth == null) {
            log.warn("YearMonth parameter not found in method arguments. Cache was not invalidated.");
            return;
        }

        String cacheKeyPattern = invalidateMonthlyStatisticsCache.cacheName() + "::" + yearMonth.toString() + "*";
        RLock lock = redissonClient.getLock("monthlyStatisticsLock:" + yearMonth);
        try {
            if (lock.tryLock(10, 3, TimeUnit.SECONDS)) {
                // 캐시 삭제
                evictCacheByPattern(cacheKeyPattern);
            } else {
                log.warn("Failed to acquire lock for cache eviction on YearMonth: {}", yearMonth);
            }
        } catch (InterruptedException e) {
            log.error("Interrupted while trying to acquire lock for YearMonth: {}", yearMonth, e);
        } finally {
            lock.unlock();
        }
    }

    // 메서드 인자에서 YearMonth 추출 메서드
    private YearMonth extractYearMonthFromArgs(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof YearMonth) {
                return (YearMonth) arg;
            }
        }
        return null;
    }
}