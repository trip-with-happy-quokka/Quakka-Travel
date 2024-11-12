package com.sparta.quokkatravel.domain.coupon.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CouponLockUtil {

    private static final int EMPTY = 0;

    // 분산 락을 사용하여 볼륨 감소
    public static void decreaseVolumeWithLock(RedissonClient redissonClient, final String key) {
        final String lockName = key + ":lock";
        final RLock lock = redissonClient.getLock(lockName);
        final String threadName = Thread.currentThread().getName();

        try {
            if (!lock.tryLock(1, 3, TimeUnit.SECONDS)) {
                return;
            }

            final int quantity = (int) redissonClient.getBucket(key).get();
            System.out.println("quantity : " + quantity);
            if (quantity <= EMPTY) {
                log.info("threadName : {} / 사용 가능 쿠폰 모두 소진", threadName);
                return;
            }

            log.info("threadName : {} / 사용 가능 쿠폰 수량 : {}개", threadName, quantity);
            redissonClient.getBucket(key).set(quantity - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    // 분산 락 없이 볼륨 감소
    public static void decreaseVolumeWithoutLock(RedissonClient redissonClient, final String key) {
        final String threadName = Thread.currentThread().getName();
        final int quantity = (int) redissonClient.getBucket(key).get();

        if (quantity <= EMPTY) {
            log.info("threadName : {} / 사용 가능 쿠폰 모두 소진", threadName);
            return;
        }

        log.info("threadName : {} / 사용 가능 쿠폰 수량 : {}개", threadName, quantity);
        redissonClient.getBucket(key).set(quantity - 1);
    }
}
