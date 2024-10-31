package com.sparta.quokkatravel.domain.coupon.service;


import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponCodeRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponCodeResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponDeleteResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponRedeemResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;

import java.util.List;

public interface CouponService {

    CouponResponseDto createEventCoupon(String email, Long eventId, CouponRequestDto couponRequestDto);
    CouponResponseDto createAccommodationCoupon(String email, Long accommodationId, CouponRequestDto couponRequestDto);
    CouponCodeResponseDto registerCouponWithLock(String email, Long userId, CouponCodeRequestDto couponCodeRequestDto);
    CouponCodeResponseDto registerCouponWithoutLock(String email, Long userId, CouponCodeRequestDto couponCodeRequestDto);
    CouponRedeemResponseDto redeemCoupon(String email, Long userId, Long couponId);
    List<CouponResponseDto> getAllMyCoupons(String email, Long userId);
    CouponDeleteResponseDto deleteCoupon(String email, Long couponId);

}
