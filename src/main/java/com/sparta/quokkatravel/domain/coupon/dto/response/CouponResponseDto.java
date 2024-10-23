package com.sparta.quokkatravel.domain.coupon.dto.response;

import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CouponResponseDto {

    private final String couponName;
    private final CouponType couponType;
    private final String couponCode;
    private final int discountRate;
    private final int discountAmount;
    private final LocalDate validFrom;
    private final LocalDate validUntil;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CouponResponseDto(String couponName, CouponType couponType, String couponCode, int discountRate, int discountAmount, LocalDate validFrom, LocalDate validUntil, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.couponName = couponName;
        this.couponType = couponType;
        this.couponCode = couponCode;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
