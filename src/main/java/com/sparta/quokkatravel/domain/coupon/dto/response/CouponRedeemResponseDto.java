package com.sparta.quokkatravel.domain.coupon.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponRedeemResponseDto {

    // TODO [1] 수정해야함
    private final Long couponId;
    private final String couponName;
    private final String couponCode;
    private final int discountRate;
    private final int discountAmount;
    private final LocalDate validFrom;
    private final LocalDate validUntil;


    public CouponRedeemResponseDto(Long couponId, String couponName, String couponCode, int discountRate, int discountAmount, LocalDate validFrom, LocalDate validUntil) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }
}
