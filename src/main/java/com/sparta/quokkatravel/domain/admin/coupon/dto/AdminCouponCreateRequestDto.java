package com.sparta.quokkatravel.domain.admin.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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