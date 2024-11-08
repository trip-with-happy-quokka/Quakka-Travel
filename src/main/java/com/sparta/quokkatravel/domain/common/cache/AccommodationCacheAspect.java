package com.sparta.quokkatravel.domain.common.cache;

import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.room.entity.Room;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Slf4j
@Component
public class AccommodationCacheAspect extends AbstractCacheAspect {

    private final AccommodationRepository accommodationRepository;
    private final RedissonClient redissonClient;

    public AccommodationCacheAspect(RedisTemplate<String, Object> redisTemplate, AccommodationRepository accommodationRepository, RedissonClient redissonClient) {
        super(redisTemplate);
        this.accommodationRepository = accommodationRepository;
        this.redissonClient = redissonClient;
    }
    
    @Before("@annotation(com.sparta.quokkatravel.domain.common.cache.InvalidateAccommodationCache)")
    public void evictAccommodationCache(JoinPoint joinPoint) {
        Long accommodationId = extractIdFromArgs(joinPoint);
        if (accommodationId == null) {
            log.warn("Accommodation ID not found in method arguments. Cache was not invalidated.");
            return;
        }

        RLock lock = redissonClient.getLock("accommodationLock: " + accommodationId);
        try {
            if (lock.tryLock(10, 3, TimeUnit.SECONDS)) {
                // Accommodation 캐시 삭제
                evictCacheByPattern("Accommodation::" + accommodationId + "*");

                // 만약 삭제 작업일 경우에만 Room 캐시 삭제 수행
                if (isDeleteOperation(joinPoint)) {
                    deleteAssociatedRoomCaches(accommodationId);
                }
            } else {
                log.warn("Failed to acquire lock for cache eviction on accommodation ID: {}", accommodationId);
            }
        } catch (InterruptedException e) {
            log.error("Interrupted while trying to acquire lock for accommodation ID: {}", accommodationId, e);
        } finally {
            lock.unlock();
        }
    }

    // delete 인지 확인
    private boolean isDeleteOperation(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName().startsWith("delete");
    }

    // 해당 숙소와 연관된 객실에 대한 캐시 삭제하기
    private void deleteAssociatedRoomCaches(Long accommodationId) {
        try {
            List<Long> roomIdList = accommodationRepository.findById(accommodationId)
                    .orElseThrow(() -> new NotFoundException("Accommodation not found"))
                    .getRooms().stream().map(Room::getId).toList();

            for (Long roomId : roomIdList) {
                evictCacheByPattern("Room::" + roomId + "*");
            }
        } catch (NotFoundException e) {
            log.error("Failed to retrieve rooms for Accommodation ID: " + accommodationId, e);
        }
    }
}
