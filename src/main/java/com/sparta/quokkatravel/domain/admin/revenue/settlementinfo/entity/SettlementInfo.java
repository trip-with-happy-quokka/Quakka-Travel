package com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.entity;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.entity.RevenueDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "settlement_info")
public class SettlementInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settlementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;    // 정산 관련 숙소

    private BigDecimal totalIncome;         // 총 수익
    private int totalReservationCount;      // 총 예약 수
    private int canceledReservationCount;   // 정산 기간 동안 취소된 예약
    private BigDecimal refundAmount;        // 환불된 금액 합계
    private BigDecimal platformCommission;  // 플랫폼 수수료
    private BigDecimal partnerCommission;   // 파트너 수수료
    private BigDecimal taxAmount;           // 세금 금액

    @Enumerated(EnumType.STRING)
    private SettlementStatus settlementStatus;  // 정산 상태 (진행 중, 완료, 취소)

    private LocalDate settlementPeriodStart;    // 정산 시작 날짜
    private LocalDate settlementPeriodEnd;      // 정산 종료 날짜
    private LocalDate settlementDate;           // 정산 완료 날짜

    @OneToMany(mappedBy = "settlementInfo", cascade = CascadeType.ALL)
    private List<RevenueDetail> revenueDetails; // 수익 내역 연결

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 정산 정보 수정 메서드
    public SettlementInfo(Accommodation accommodation, BigDecimal totalIncome, int totalReservationCount,
                          int canceledReservationCount, BigDecimal refundAmount, BigDecimal platformCommission,
                          BigDecimal partnerCommission, BigDecimal taxAmount, SettlementStatus settlementStatus,
                          LocalDate settlementPeriodStart, LocalDate settlementPeriodEnd) {
        this.accommodation = accommodation;
        this.totalIncome = totalIncome;
        this.totalReservationCount = totalReservationCount;
        this.canceledReservationCount = canceledReservationCount;
        this.refundAmount = refundAmount;
        this.platformCommission = platformCommission;
        this.partnerCommission = partnerCommission;
        this.taxAmount = taxAmount;
        this.settlementStatus = settlementStatus;
        this.settlementPeriodStart = settlementPeriodStart;
        this.settlementPeriodEnd = settlementPeriodEnd;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(BigDecimal totalIncome, int totalReservationCount, int canceledReservationCount,
                       BigDecimal refundAmount, BigDecimal platformCommission, BigDecimal partnerCommission,
                       BigDecimal taxAmount, LocalDate settlementPeriodStart, LocalDate settlementPeriodEnd) {
        this.totalIncome = totalIncome;
        this.totalReservationCount = totalReservationCount;
        this.canceledReservationCount = canceledReservationCount;
        this.refundAmount = refundAmount;
        this.platformCommission = platformCommission;
        this.partnerCommission = partnerCommission;
        this.taxAmount = taxAmount;
        this.settlementPeriodStart = settlementPeriodStart;
        this.settlementPeriodEnd = settlementPeriodEnd;
        this.updatedAt = LocalDateTime.now();  // 업데이트 시간 갱신
    }
}