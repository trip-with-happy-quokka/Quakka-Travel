package com.sparta.quokkatravel.domain.coupon.service;


import com.sparta.quokkatravel.domain.coupon.dto.request.CouponRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;

public interface CouponService {

    CouponResponseDto createCoupon(CouponRequestDto couponRequestDto);
    CouponResponseDto getCoupons();
    CouponResponseDto deleteCoupon();
}
