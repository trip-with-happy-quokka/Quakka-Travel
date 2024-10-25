package com.sparta.quokkatravel.domain.admin.coupon.service;

import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponCreateRequestDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponResponseDto;
import com.sparta.quokkatravel.domain.admin.coupon.repository.AdminCouponRepository;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminCouponService {

    private final AdminCouponRepository adminCouponRepository;

    public AdminCouponService(AdminCouponRepository adminCouponRepository) {
        this.adminCouponRepository = adminCouponRepository;
    }

    // 모든 쿠폰 조회
    public List<AdminCouponResponseDto> getAllCoupons() {
        List<Coupon> coupons = adminCouponRepository.findAll();
        return coupons.stream()
                .map(AdminCouponResponseDto::new)
                .collect(Collectors.toList());
    }

    // 쿠폰 발급
    public AdminCouponResponseDto createCoupon(AdminCouponCreateRequestDto couponRequestDto) {
        // DTO에서 code가 null일 경우 자동으로 UUID로 코드 생성
        String couponCode = couponRequestDto.getCode() != null ? couponRequestDto.getCode() : UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        Coupon coupon = new Coupon(
                couponCode,                             // code 값
                couponRequestDto.getName(),             // name 값
                couponRequestDto.getDiscountAmount(),   // 할인 금액
                couponRequestDto.getValidFrom(),        // 유효 시작일
                couponRequestDto.getValidUntil(),       // 유효 마감일
                couponRequestDto.getCouponType(),       // 쿠폰 타입
                couponRequestDto.getContent());         // content 값
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