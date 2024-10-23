package com.sparta.quokkatravel.domain.admin.coupon.dto;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminCouponResponseDto {

    private Long couponId;
    private String code;
    private int discountAmount;
    private LocalDateTime ValidUntil;

    public AdminCouponResponseDto(Coupon coupon) {
        this.couponId = coupon.getId();
        this.code = coupon.getName();
        this.discountAmount = coupon.getDiscountAmount();
        this.ValidUntil = coupon.getValidUntil();
    }
}