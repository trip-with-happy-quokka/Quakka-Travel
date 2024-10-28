package com.sparta.quokkatravel.domain.admin.revenue.revenuedetail.entity;

import com.sparta.quokkatravel.domain.admin.revenue.settlementinfo.entity.SettlementInfo;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "revenue_detail")
public class RevenueDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long revenueId;  // 수익 내역 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_id", nullable = false)
    private SettlementInfo settlementInfo;  // 정산 정보와의 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;  // 예약 정보와의 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = true)
    private Coupon coupon;  // 쿠폰 정보와의 관계 (옵션)

    private String productName;  // 예약된 상품명
    private BigDecimal productPrice;  // 상품 가격
    private String paymentMethod;  // 결제 수단
    private BigDecimal additionalIncome;  // 추가 수익
    private BigDecimal refundAmount;  // 환불된 금액

    private LocalDateTime createdAt;  // 생성 날짜
    private LocalDateTime updatedAt;  // 마지막 수정 날짜

    public RevenueDetail() {}

    // 수익 내역 수정 메서드
    public RevenueDetail(SettlementInfo settlementInfo, Reservation reservation, String productName,
                         BigDecimal productPrice, String paymentMethod, BigDecimal additionalIncome,
                         BigDecimal refundAmount, Coupon coupon) {
        this.settlementInfo = settlementInfo;
        this.reservation = reservation;
        this.productName = productName;
        this.productPrice = productPrice;
        this.paymentMethod = paymentMethod;
        this.additionalIncome = additionalIncome;
        this.refundAmount = refundAmount;
        this.coupon = coupon;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 수익 내역 필드 업데이트
    public void update(String productName, BigDecimal productPrice, String paymentMethod,
                       BigDecimal additionalIncome, BigDecimal refundAmount, Coupon coupon) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.paymentMethod = paymentMethod;
        this.additionalIncome = additionalIncome;
        this.refundAmount = refundAmount;
        this.coupon = coupon;
        this.updatedAt = LocalDateTime.now();  // 업데이트 시점의 시간으로 갱신
    }
}