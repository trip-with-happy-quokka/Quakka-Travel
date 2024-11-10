package com.sparta.quokkatravel.domain.coupon.service;

import com.sparta.quokkatravel.domain.common.exception.BadRequestException;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponCodeRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponCodeResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponDeleteResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponRedeemResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import com.sparta.quokkatravel.domain.search.repository.CouponSearchRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final RedissonClient redissonClient;
    private final CouponSearchRepository couponSearchRepository;
    private final int EMPTY = 0;

    // 쿠폰 등록 메서드 ( 분산락 사용 )
    @Override
    @Transactional
    public CouponCodeResponseDto registerCoupon(String email, Long userId, CouponCodeRequestDto couponCodeRequestDto) {


        // userId 로 User 조회
        User user = userRepository.findByEmailOrElseThrow(email);

        // 쿠폰 코드로 쿠폰 찾기
        Coupon coupon = couponRepository.findByCode(couponCodeRequestDto.getCouponCode())
                .orElseThrow(() -> new NotFoundException("coupon is not found"));


//            // 동시성 제어
//            // 쿠폰 발급 (volume 하나 감소)
//            coupon.decreaseVolume();
        String key = coupon.getCode();

        decreaseVolumeWithLock(key);


            // 쿠폰 소유자 및 등록일자 등록
            // 쿠폰 사용 가능 상태로 변경
            coupon.registerCoupon(user);

        return new CouponCodeResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getCouponStatus(),
                coupon.getValidFrom(),
                coupon.getValidUntil()
        );
    }

    public void decreaseVolumeWithLock(final String key) {
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

    public void decreaseVolumeWithoutLock(final String key) {
        final String threadName = Thread.currentThread().getName();
        final int quantity = (int) redissonClient.getBucket(key).get();

        if (quantity <= EMPTY) {
            log.info("threadName : {} / 사용 가능 쿠폰 모두 소진", threadName);
            return;
        }

        log.info("threadName : {} / 사용 가능 쿠폰 수량 : {}개", threadName, quantity);
        redissonClient.getBucket(key).set(quantity - 1);
    }

    // 쿠폰 사용 메서드
    @Override
    @Transactional
    public CouponRedeemResponseDto redeemCoupon(String email, Long userId, Long couponId){

        // userId 로 User 조회
        User user = userRepository.findByEmailOrElseThrow(email);

        // couponId 로 Coupon 조회
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(()-> new NotFoundException("쿠폰 조회 불가"));

        // coupon 상태 => 사용됨으로 변경
        coupon.redeemCoupon();
        couponRepository.save(coupon);

        return new CouponRedeemResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getCouponStatus(),
                coupon.getDiscountRate(),
                coupon.getDiscountAmount(),
                coupon.getValidFrom(),
                coupon.getValidUntil()
        );
    }

    // 내 쿠폰 전체 조회
    @Override
    public List<CouponResponseDto> getAllMyCoupons(String email, Long userId) {

        // User 정보 조회
        User user = userRepository.findByEmailOrElseThrow(email);

        // 유저 정보 일치하는지 확인
        if(!email.equals(user.getEmail())){
            throw new BadRequestException("본인 쿠폰만 조회 가능합니다.");
        }

        List<Coupon> coupons = couponRepository.findAllByOwner(user);

        return coupons.stream().map(coupon -> new CouponResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCouponType(),
                coupon.getVolume(),
                coupon.getCode(),
                coupon.getCouponStatus(),
                coupon.getDiscountRate(),
                coupon.getDiscountAmount(),
                coupon.getValidFrom(),
                coupon.getValidUntil(),
                coupon.getCreatedAt(),
                coupon.getUpdatedAt()
        )).toList();
    }

    // 쿠폰 삭제
    @Override
    @Transactional
    public CouponDeleteResponseDto deleteCoupon(String email, Long couponId) {

        // 일단 내가 이 쿠폰 갖고 있는지 확인이 필요함

        // couponId 로 해당 쿠폰 조회
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("해당 쿠폰 조회 불가"));

        // 쿠폰 삭제여부 변경
        coupon.deleteCoupon();
        log.info("Coupon deleted: {}", coupon);
        couponRepository.save(coupon);

        // CouponDocument Create For ElasticSearch
        CouponDocument couponDocument = couponSearchRepository.findByCouponIdOrElseThrow(couponId);
        log.info("CouponDocument deleted: {}", couponDocument);
        couponSearchRepository.delete(couponDocument);

        return new CouponDeleteResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getIsDeleted(),
                coupon.getDeletedAt()
        );
    }

}
