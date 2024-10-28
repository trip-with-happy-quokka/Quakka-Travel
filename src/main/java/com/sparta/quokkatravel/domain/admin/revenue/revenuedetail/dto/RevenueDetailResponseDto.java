package com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RevenueDetailResponseDto {

    private String productName;
    private BigDecimal productPrice;
    private LocalDate reservationDate;
    private String couponName; // 쿠폰이 없으면 "No Coupon"
    private String paymentMethod;

    public RevenueDetailResponseDto(String productName, BigDecimal productPrice, LocalDate reservationDate,
                                    String couponName, String paymentMethod) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.reservationDate = reservationDate;
        this.couponName = couponName;
        this.paymentMethod = paymentMethod;
    }
}
