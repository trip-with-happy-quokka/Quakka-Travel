package com.sparta.quokkatravel.domain.coupon.entity;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Enumerated(EnumType.STRING)
    @Column
    private CouponStatus couponStatus;

    @Min(0)
    @Max(100)
    @Column(name = "discount_rate")
    private Integer discountRate;

    @Min(0)
    @Column(name = "discount_amount")
    private Integer discountAmount;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

    @Column(name = "is_available")
    private Boolean isAvailable = false;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")  // 생성자에 대한 외래 키
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")  // 소유자에 대한 외래 키
    private User owner;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    public Coupon(String couponName, String couponContent, String couponType, String couponCode,
                  int discountRate, int discountAmount, LocalDate validFrom, LocalDate validUntil,
                  Event event, User createdBy) {
        this.name = couponName;
        this.content = couponContent;
        this.couponType = CouponType.valueOf(couponType);
        this.code = couponCode;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.event = event;
        this.createdBy = createdBy;
    }

    public Coupon(String couponName, String couponContent, String couponType, String couponCode,
                  int discountRate, int discountAmount, LocalDate validFrom, LocalDate validUntil,
                  Accommodation accommodation, User createdBy) {
        this.name = couponName;
        this.content = couponContent;
        this.couponType = CouponType.valueOf(couponType);
        this.code = couponCode;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.accommodation = accommodation;
        this.createdBy = createdBy;
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

    public void deleteCoupon() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();  // 삭제 시점 저장
    }

    public void registerCoupon(User user) {
        this.owner = user;
        this.registeredAt = LocalDateTime.now();
        this.couponStatus = CouponStatus.REGISTERED;
    }
}
