package com.sparta.quokkatravel.domain.admin.coupon.service;

import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponCreateRequestDto;
import com.sparta.quokkatravel.domain.admin.coupon.dto.AdminCouponResponseDto;
import com.sparta.quokkatravel.domain.admin.coupon.repository.AdminCouponRepository;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Coupon coupon = new Coupon(couponRequestDto.getCode(), couponRequestDto.getDiscountAmount(), couponRequestDto.getValidUntil());
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