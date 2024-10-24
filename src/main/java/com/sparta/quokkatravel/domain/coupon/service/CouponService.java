package com.sparta.quokkatravel.domain.coupon.service;


import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;

import java.util.List;

public interface CouponService {

    CouponResponseDto createEventCoupon(CustomUserDetails customUserDetails, Long eventId, CouponRequestDto couponRequestDto);
    CouponResponseDto createAccommodationCoupon(CustomUserDetails customUserDetails, Long accommodationId, CouponRequestDto couponRequestDto);
    List<CouponResponseDto> getAllCoupons(CustomUserDetails customUserDetails);
    void deleteCoupon(CustomUserDetails customUserDetails, Long couponId);
}
