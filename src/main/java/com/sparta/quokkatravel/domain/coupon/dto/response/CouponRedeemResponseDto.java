package com.sparta.quokkatravel.domain.coupon.dto.response;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponRedeemResponseDto {

    private final Long couponId;
    private final String couponName;
    private final String couponCode;
    private final CouponStatus couponStatus;
    private final int discountRate;
    private final int discountAmount;
    private final LocalDate validFrom;
    private final LocalDate validUntil;


    public CouponRedeemResponseDto(Long couponId, String couponName, String couponCode, CouponStatus couponStatus, int discountRate, int discountAmount, LocalDate validFrom, LocalDate validUntil) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.couponStatus = couponStatus;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }
}
