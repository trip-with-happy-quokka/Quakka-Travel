package com.sparta.quokkatravel.domain.coupon.dto.response;

import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CouponResponseDto {

    private final Long couponId;
    private final String couponName;
    private final CouponType couponType;
    private final Integer volume;
    private final String couponCode;
    private final CouponStatus couponStatus;
    private final int discountRate;
    private final int discountAmount;
    private final LocalDate validFrom;
    private final LocalDate validUntil;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public CouponResponseDto(Long couponId, String couponName, CouponType couponType, Integer volume, String couponCode, CouponStatus couponStatus, int discountRate, int discountAmount, LocalDate validFrom, LocalDate validUntil, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponType = couponType;
        this.volume = volume;
        this.couponCode = couponCode;
        this.couponStatus = couponStatus;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
