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

    CouponResponseDto createEventCoupon(CustomUserDetails customUserDetails, Long eventId, CouponRequestDto couponRequestDto);
    CouponResponseDto createAccommodationCoupon(CustomUserDetails customUserDetails, Long accommodationId, CouponRequestDto couponRequestDto);
    CouponCodeResponseDto registerCoupon(CustomUserDetails customUserDetails, Long userId, CouponCodeRequestDto couponCodeRequestDto);
    CouponRedeemResponseDto redeemCoupon(CustomUserDetails customUserDetails, Long userId, Long couponId);
    List<CouponResponseDto> getAllMyCoupons(CustomUserDetails customUserDetails, Long userId);
    CouponDeleteResponseDto deleteCoupon(CustomUserDetails customUserDetails, Long couponId);

}
