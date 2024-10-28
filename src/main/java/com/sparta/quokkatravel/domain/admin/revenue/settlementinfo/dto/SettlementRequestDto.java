package com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SettlementRequestDto {

    private Long accommodationId;
    private BigDecimal totalIncome;
    private int totalReservationCount;
    private int canceledReservationCount;
    private BigDecimal refundAmount;
    private BigDecimal platformCommission;
    private BigDecimal partnerCommission;
    private BigDecimal taxAmount;
    private LocalDate settlementPeriodStart;
    private LocalDate settlementPeriodEnd;
}
