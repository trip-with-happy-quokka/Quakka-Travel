package com.sparta.quokkatravel.domain.admin.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AdminCouponCreateRequestDto {

    private String code;
    private int discountAmount;
    private LocalDate validUntil;

    public AdminCouponCreateRequestDto(String code, int discountAmount, LocalDate validuntil) {
        this.code = code;
        this.discountAmount = discountAmount;
        this.validUntil = validuntil;
    }
}