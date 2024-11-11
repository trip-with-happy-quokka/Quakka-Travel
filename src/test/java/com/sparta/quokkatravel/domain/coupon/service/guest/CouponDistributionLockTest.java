package com.sparta.quokkatravel.domain.coupon.service.guest;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.coupon.service.CouponServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CouponDistributionLockTest {

    @Autowired
    private CouponServiceImpl couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    @Order(1)
    void 쿠폰등록_분산락_적용() throws InterruptedException {

        Long couponId = 1L;
        Optional<Coupon> coupon = couponRepository.findById(couponId);

        final int numberOfMember = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(numberOfMember);

        String couponkey = coupon.get().createCouponCode();

        // 100 개 카운트 넣어주기
        redissonClient.getBucket(couponkey).set(100);

        List<Thread> threadList = Stream
                .generate(() -> new Thread(new UsingLockCoupon(couponkey, countDownLatch)))
                .limit(numberOfMember)
                .collect(Collectors.toList());

        threadList.forEach(Thread::start);
        countDownLatch.await();

    }

    @Test
    @Order(2)
    void 쿠폰등록_분산락_미적용() throws InterruptedException {

        Long couponId = 1L;
        Optional<Coupon> coupon = couponRepository.findById(couponId);

        final int numberOfMember = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(numberOfMember);

        String couponkey = coupon.get().createCouponCode();

        // 100 개 카운트 넣어주기
        redissonClient.getBucket(couponkey).set(100);

        List<Thread> threadList = Stream
                .generate(() -> new Thread(new NoLockCoupon(couponkey, countDownLatch)))
                .limit(numberOfMember)
                .collect(Collectors.toList());

        threadList.forEach(Thread::start);
        countDownLatch.await();

    }


    private class UsingLockCoupon implements Runnable {
        private final String couponKey;
        private final CountDownLatch countDownLatch;

        public UsingLockCoupon(String couponKey, CountDownLatch countDownLatch) {
            this.couponKey = couponKey;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            couponService.decreaseVolumeWithLock(this.couponKey);
            countDownLatch.countDown();
        }
    }

    private class NoLockCoupon implements Runnable {
        private final String couponKey;
        private final CountDownLatch countDownLatch;

        public NoLockCoupon(String couponKey, CountDownLatch countDownLatch) {
            this.couponKey = couponKey;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            couponService.decreaseVolumeWithoutLock(this.couponKey);
            countDownLatch.countDown();
        }
    }
}
