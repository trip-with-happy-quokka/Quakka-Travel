package com.sparta.quokkatravel.domain.coupon.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
//import com.sparta.quokkatravel.domain.common.distributedLock.annotation.DistributedLock;
import com.sparta.quokkatravel.domain.common.exception.BadRequestException;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponCodeRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponCodeResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponDeleteResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponRedeemResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.event.repository.EventRepository;
//import com.sparta.quokkatravel.domain.search.document.CouponDocument;
//import com.sparta.quokkatravel.domain.search.repository.CouponSearchRepository;
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

import static java.lang.System.currentTimeMillis;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final EventRepository eventRepository;
    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;
    private final RedissonClient redissonClient;
    private final int EMPTY = 0;
//    private final CouponSearchRepository couponSearchRepository;

    // 행사 쿠폰 발급 메서드
    @Override
    @Transactional
    public CouponResponseDto createEventCoupon(String email, Long eventId, CouponRequestDto couponRequestDto) {

        // customUserDetails 에서 생성자 정보 불러오기
        User user = userRepository.findByEmailOrElseThrow(email);

        // eventId 로 event 조회
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("해당 행사 조회 불가"));

        // UUID 로 쿠폰코드 발급
        String newCouponCode = new Coupon().createCouponCode();

        // RequestDto 데이터를 Coupon 에 전달
        Coupon newCoupon = new Coupon(
                couponRequestDto.getCouponName(),
                couponRequestDto.getCouponContent(),
                couponRequestDto.getCouponType(),
                couponRequestDto.getVolume(),
                newCouponCode,
                CouponStatus.ISSUED,
                couponRequestDto.getDiscountRate(),
                couponRequestDto.getDiscountAmount(),
                couponRequestDto.getValidFrom(),
                couponRequestDto.getValidUntil(),
                event,
                user
        );

        // 쿠폰 레퍼지토리에 쿠폰 데이터를 저장 (save)
        Coupon savedCoupon = couponRepository.save(newCoupon);
//        CouponDocument couponDocument = new CouponDocument(savedCoupon);
//        couponSearchRepository.save(couponDocument);

        // 쿠폰이 발급 될 때, 수량이 몇개인지 레디스에 넣어주기


        // 쿠폰을 CouponResponseDto 로 반환
        return new CouponResponseDto(
                savedCoupon.getId(),
                savedCoupon.getName(),
                savedCoupon.getCouponType(),
                savedCoupon.getVolume(),
                newCouponCode,
                savedCoupon.getCouponStatus(),
                savedCoupon.getDiscountRate(),
                savedCoupon.getDiscountAmount(),
                savedCoupon.getValidFrom(),
                savedCoupon.getValidFrom(),
                savedCoupon.getCreatedAt(),
                savedCoupon.getUpdatedAt()
        );
    }

    // 숙소 쿠폰 발급 메서드
    @Override
    @Transactional
    public CouponResponseDto createAccommodationCoupon(String email, Long accommodationId, CouponRequestDto couponRequestDto) {

        // customUserDetails 에서 생성자 정보 불러오기
        User user = userRepository.findByEmailOrElseThrow(email);

        // accommodationId 로 accommodation 조회
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(() -> new NotFoundException("해당 숙소 조회 불가"));

        // UUID 로 쿠폰코드 발급
        String newCouponCode = new Coupon().createCouponCode();


        // RequestDto 데이터를 Coupon 에 전달
        Coupon newCoupon = new Coupon(
                couponRequestDto.getCouponName(),
                couponRequestDto.getCouponContent(),
                couponRequestDto.getCouponType(),
                couponRequestDto.getVolume(),
                newCouponCode,
                CouponStatus.ISSUED,
                couponRequestDto.getDiscountRate(),
                couponRequestDto.getDiscountAmount(),
                couponRequestDto.getValidFrom(),
                couponRequestDto.getValidUntil(),
                accommodation,
                user
        );

        // 쿠폰 레퍼지토리에 쿠폰 데이터를 저장 (save)
        Coupon savedCoupon = couponRepository.save(newCoupon);

        // 쿠폰을 CouponResponseDto 로 반환
        return new CouponResponseDto(
                savedCoupon.getId(),
                savedCoupon.getName(),
                savedCoupon.getCouponType(),
                savedCoupon.getVolume(),
                newCouponCode,
                savedCoupon.getCouponStatus(),
                savedCoupon.getDiscountRate(),
                savedCoupon.getDiscountAmount(),
                savedCoupon.getValidFrom(),
                savedCoupon.getValidUntil(),
                savedCoupon.getCreatedAt(),
                savedCoupon.getUpdatedAt()
        );
    }

    // 쿠폰 등록 메서드 ( 분산락 사용 )
    @Override
    @Transactional
    public CouponCodeResponseDto registerCoupon(String email, Long userId, CouponCodeRequestDto couponCodeRequestDto) {

        // -------------------------- 유효성 검사 ------------------------------------------
        // userId 로 User 조회
        User user = userRepository.findByEmailOrElseThrow(email);

        // 쿠폰 코드로 쿠폰 찾기
        Coupon coupon = couponRepository.findByCode(couponCodeRequestDto.getCouponCode())
                .orElseThrow(() -> new NotFoundException("coupon is not found"));
        // -------------------------------------------------------------------------------

//            // 동시성 제어
//            // 쿠폰 발급 (volume 하나 감소)
//            coupon.decreaseVolume();
        String key = coupon.getCode() + currentTimeMillis();

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

            final int quantity = usableCoupon(key);
            System.out.println("quantity : " + quantity);
            if (quantity <= EMPTY) {
                log.info("threadName : {} / 사용 가능 쿠폰 모두 소진", threadName);
                return;
            }

            log.info("threadName : {} / 사용 가능 쿠폰 수량 : {}개", threadName, quantity);
            setUsableCoupon(key, quantity - 1);
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
        final int quantity = usableCoupon(key);

        if (quantity <= EMPTY) {
            log.info("threadName : {} / 사용 가능 쿠폰 모두 소진", threadName);
            return;
        }

        log.info("threadName : {} / 사용 가능 쿠폰 수량 : {}개", threadName, quantity);
        setUsableCoupon(key, quantity - 1);
    }

    public String keyResolver(String code) {
        return "COUPON:" + code;
    }

    public void setUsableCoupon(String key, int quantity) {
        redissonClient.getBucket(key).set(quantity);
    }

    public int usableCoupon(String key) {
        return (int) redissonClient.getBucket(key).get();
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

        // couponId 로 해당 쿠폰 조회
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("해당 쿠폰 조회 불가"));

        // 쿠폰 삭제여부 변경
        coupon.deleteCoupon();
        couponRepository.save(coupon);

        return new CouponDeleteResponseDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getCode(),
                coupon.getIsDeleted(),
                coupon.getDeletedAt()
        );
    }

}
