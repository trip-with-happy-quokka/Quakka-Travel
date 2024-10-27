package com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class RevenueDetailRequestDto {

    private Long settlementId;
    private Long reservationId;
    private String productName;
    private BigDecimal productPrice;
    private String paymentMethod;
    private BigDecimal additionalIncome;
    private BigDecimal refundAmount;
    private Long couponId;
}
