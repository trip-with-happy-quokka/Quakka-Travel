package com.sparta.quokkatravel.domain.admin.coupon.dto;

import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AdminCouponCreateRequestDto {

    private String code;
    private String name;
    private int discountAmount;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private CouponType couponType;
    private String content;

    public AdminCouponCreateRequestDto(String code, String name, int discountAmount, LocalDate validFrom, LocalDate validUntil, CouponType couponType, String content) {
        this.code = code;
        this.name = name;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.couponType = couponType;
        this.content = content;
    }
}