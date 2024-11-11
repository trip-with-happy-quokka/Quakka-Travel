package com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.service;

import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.dto.SettlementResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class MonthlyStatisticsCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public MonthlyStatisticsCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheMonthlyStatistics(String monthKey, List<SettlementResponseDto> statistics) {
        redisTemplate.opsForValue().set(monthKey, statistics, Duration.ofDays(30)); // 30일 동안 캐싱
    }

    @SuppressWarnings("unchecked")
    public List<SettlementResponseDto> getCachedMonthlyStatistics(String monthKey) {
        return (List<SettlementResponseDto>) redisTemplate.opsForValue().get(monthKey);
    }

    public void deleteMonthlyStatistics(String monthKey) {
        redisTemplate.delete(monthKey);
    }
}