package com.sparta.quokkatravel.domain.coupon.service;


import com.sparta.quokkatravel.domain.coupon.dto.request.CouponCodeRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponGiftRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.*;

import java.util.List;

public interface CouponService {

    CouponCodeResponseDto registerCoupon(String email, Long userId, CouponCodeRequestDto couponCodeRequestDto);
    CouponGiftResponseDto giveCouponToOther(String email, Long userId, Long couponId, CouponGiftRequestDto couponGiftRequestDto);
    CouponRedeemResponseDto redeemCoupon(String email, Long userId, Long couponId);
    List<CouponResponseDto> getAllMyCoupons(String email, Long userId);
    CouponDeleteResponseDto deleteCoupon(String email, Long couponId);
}
