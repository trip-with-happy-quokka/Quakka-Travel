package com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.dto.RevenueDetailResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class SettlementResponseDto {

    private Long settlementId;
    private Long accommodationId;
    private BigDecimal totalIncome;
    private int totalReservationCount;
    private int canceledReservationCount;
    private BigDecimal refundAmount;
    private BigDecimal platformCommission;
    private BigDecimal partnerCommission;
    private BigDecimal taxAmount;

    private String settlementPeriodStart;
    private String settlementPeriodEnd;

    private List<RevenueDetailResponseDto> revenueDetails;

    public SettlementResponseDto(Long settlementId, Long accommodationId, BigDecimal totalIncome,
                                 int totalReservationCount, int canceledReservationCount, BigDecimal refundAmount,
                                 BigDecimal platformCommission, BigDecimal partnerCommission, BigDecimal taxAmount,
                                 LocalDate settlementPeriodStart, LocalDate settlementPeriodEnd,
                                 List<RevenueDetailResponseDto> revenueDetails) {
        this.settlementId = settlementId;
        this.accommodationId = accommodationId;
        this.totalIncome = totalIncome;
        this.totalReservationCount = totalReservationCount;
        this.canceledReservationCount = canceledReservationCount;
        this.refundAmount = refundAmount;
        this.platformCommission = platformCommission;
        this.partnerCommission = partnerCommission;
        this.taxAmount = taxAmount;
        this.settlementPeriodStart = settlementPeriodStart.toString();
        this.settlementPeriodEnd = settlementPeriodEnd.toString();
        this.revenueDetails = revenueDetails;
    }

}
