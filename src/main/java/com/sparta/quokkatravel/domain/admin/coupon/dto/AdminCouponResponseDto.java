package com.sparta.quokkatravel.domain.admin.coupon.dto;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminCouponResponseDto {

    private Long couponId;
    private String couponName;
    private CouponType couponType;
    private Integer volume;
    private String couponCode;
    private CouponStatus couponStatus;
    private Integer discountRate;
    private Integer discountAmount;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public AdminCouponResponseDto(Coupon coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getName();
        this.couponType = coupon.getCouponType();
        this.volume = coupon.getVolume();
        this.couponCode = coupon.getCode();
        this.couponStatus = coupon.getCouponStatus();
        this.discountRate = coupon.getDiscountRate();
        this.discountAmount = coupon.getDiscountAmount();
        this.validFrom = coupon.getValidFrom();
        this.validUntil = coupon.getValidUntil();
        this.createdAt = coupon.getCreatedAt();
        this.updatedAt = coupon.getUpdatedAt();
    }

    // discountRate가 null일 경우 0 반환
    public Integer getDiscountRate() {
        return discountRate != null ? discountRate : 0;
    }

    // discountAmount가 null일 경우 0 반환
    public Integer getDiscountAmount() {
        return discountAmount != null ? discountAmount : 0;
    }
}