package com.sparta.quokkatravel.domain.coupon.dto.response;

import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponCodeResponseDto {

    private final Long couponId;
    private final String couponName;
    private final String couponCode;
    private final CouponStatus couponStatus;
    private final LocalDate validFrom;
    private final LocalDate validUntil;

    public CouponCodeResponseDto(Long couponId, String couponName, String couponCode, CouponStatus couponStatus, LocalDate validFrom, LocalDate validUntil) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.couponStatus = couponStatus;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }
}
