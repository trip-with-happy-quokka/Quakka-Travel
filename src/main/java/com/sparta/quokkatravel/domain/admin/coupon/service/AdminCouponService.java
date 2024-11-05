package com.sparta.quokkatravel.domain.admin.coupon.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponRequestDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponResponseDto;
import com.sparta.quokkatravel.domain.admin.coupon.repository.AdminCouponRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.event.repository.EventRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminCouponService {

    private final AdminCouponRepository adminCouponRepository;
    private final AccommodationRepository accommodationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RedissonClient redissonClient;

    // 쿠폰 발급
    public AdminCouponResponseDto createCoupon(String email, AdminCouponRequestDto couponRequestDto) {

        User user = userRepository.findByEmailOrElseThrow(email);

        // DTO에서 code가 null일 경우 자동으로 UUID로 코드 생성
        String couponCode = couponRequestDto.getCouponCode() != null ? couponRequestDto.getCouponCode() : UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        // 쿠폰 키 생성
        String couponkey = couponCode;

        // 쿠폰 발행 수량만큼 redis 서버에 쿠폰 수량 입력
        redissonClient.getBucket(couponkey).set(couponRequestDto.getVolume());

        Coupon coupon = new Coupon(
                couponRequestDto.getCouponName(),             // name 값
                couponRequestDto.getCouponContent(),
                couponRequestDto.getCouponType().toString(),       // 쿠폰 타입
                couponRequestDto.getVolume(),
                couponCode,
                couponRequestDto.getDiscountRate(),
                couponRequestDto.getDiscountAmount(),   // 할인 금액
                couponRequestDto.getValidFrom(),        // 유효 시작일
                couponRequestDto.getValidUntil(),       // 유효 마감일
                user
        );

        if (Objects.equals(CouponType.ACCOMMODATION, couponRequestDto.getCouponType())) {
            // 숙소 조회
            Accommodation accommodation = accommodationRepository.findById(couponRequestDto.getCouponTargetId())
                    .orElseThrow(() -> new NotFoundException("Accommodation is not found"));
            coupon.addAccommodation(accommodation);
        }
        if (Objects.equals(CouponType.EVENT, couponRequestDto.getCouponType())) {
            // 행사 조회
            Event event = eventRepository.findById(couponRequestDto.getCouponTargetId())
                    .orElseThrow(() -> new NotFoundException("Event is not found"));
            coupon.addEvent(event);
        }

        Coupon savedCoupon = adminCouponRepository.save(coupon);

        // 쿠폰을 CouponResponseDto 로 반환
        return new AdminCouponResponseDto(savedCoupon);
    }

    // 단일 쿠폰 조회
    public AdminCouponResponseDto getCoupon(Long couponId) {
        return new AdminCouponResponseDto(adminCouponRepository.findById(couponId)
                .orElseThrow(EntityNotFoundException::new));
    }


    // 모든 쿠폰 조회
    public List<AdminCouponResponseDto> getAllCoupons() {
        List<Coupon> coupons = adminCouponRepository.findAll();
        return coupons.stream()
                .map(AdminCouponResponseDto::new)
                .toList();
    }

    // 쿠폰 수정
    public AdminCouponResponseDto updateCoupon(Long couponId, AdminCouponRequestDto couponRequestDto) {
        Coupon coupon = adminCouponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("쿠폰을 찾을 수 없습니다."));

        coupon.updateCoupon(
                couponRequestDto.getCouponCode(),
                couponRequestDto.getCouponName(),
                couponRequestDto.getDiscountAmount(),
                couponRequestDto.getValidFrom(),
                couponRequestDto.getValidUntil(),
                couponRequestDto.getCouponType(),
                couponRequestDto.getCouponContent()
        );

        adminCouponRepository.save(coupon);
        return new AdminCouponResponseDto(coupon);
    }

    // 쿠폰 삭제
    public void deleteCoupon(Long couponId) {
        Coupon coupon = adminCouponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("쿠폰을 찾을 수 없습니다."));
        adminCouponRepository.delete(coupon);
    }
}