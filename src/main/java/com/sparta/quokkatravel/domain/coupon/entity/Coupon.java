package com.sparta.quokkatravel.domain.coupon.entity;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.event.entity.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Coupon extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_name", nullable = false)
    private String name;

    @Column(name = "coupon_contents", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Column(name = "coupon_code", unique = true, nullable = false)
    private String code;

    @Min(0)
    @Max(100)
    @Column(name = "discount_rate")
    private int discountRate;

    @Min(0)
    @Column(name = "discount_amount")
    private int discountAmount;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    public Coupon(String couponName, String couponContent, String couponType, String couponCode,
                  int discountRate, int discountAmount, LocalDate validFrom, LocalDate validUntil, Event event) {
        this.name = couponName;
        this.content = couponContent;
        this.couponType = CouponType.valueOf(couponType);
        this.code = couponCode;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.event = event;
    }

    public Coupon(String couponName, String couponContent, String couponType, String couponCode,
                  int discountRate, int discountAmount, LocalDate validFrom, LocalDate validUntil, Accommodation accommodation) {
        this.name = couponName;
        this.content = couponContent;
        this.couponType = CouponType.valueOf(couponType);
        this.code = couponCode;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.accommodation = accommodation;
    }

    // UUID 기반의 쿠폰 코드 생성 메서드
    public String createCouponCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    public Coupon(String name, int discountAmount, LocalDate validUntil) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.validUntil = validUntil;
    }
}
