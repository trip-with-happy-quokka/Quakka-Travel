package com.sparta.quokkatravel.domain.coupon.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponDeleteResponseDto {

    private final Long couponId;
    private final String couponName;
    private final String couponCode;
    private final Boolean isDeleted;
    private final LocalDateTime deletedAt;

    public CouponDeleteResponseDto(Long couponId, String couponName, String couponCode, Boolean isDeleted, LocalDateTime deletedAt) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponCode = couponCode;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
}
