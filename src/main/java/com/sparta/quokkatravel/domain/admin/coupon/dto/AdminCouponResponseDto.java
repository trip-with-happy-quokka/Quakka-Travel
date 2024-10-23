package com.sparta.quokkatravel.domain.admin.coupon.dto;

import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AdminCouponResponseDto {

    private Long couponId;
    private String code;
    private int discountAmount;
    private LocalDate ValidUntil;

    public AdminCouponResponseDto(Coupon coupon) {
        this.couponId = coupon.getId();
        this.code = coupon.getName();
        this.discountAmount = coupon.getDiscountAmount();
        this.ValidUntil = coupon.getValidUntil();
    }
}