package com.sparta.quokkatravel.domain.coupon.entity;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.common.shared.Timestamped;
import com.sparta.quokkatravel.domain.event.entity.Event;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_coupon_status", columnList = "coupon_status"),
        @Index(name = "idx_coupon_type", columnList = "coupon_type"),
        @Index(name = "idx_coupon_code", columnList = "coupon_code")
})
public class Coupon extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_name", nullable = false)
    private String name;

    @Column(name = "coupon_contents", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type", nullable = false)
    private CouponType couponType;

    @Min(0)
    @Column(name = "coupon_volume", nullable = false)
    private Integer volume;

    @Column(name = "coupon_code", unique = true, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_status")
    private CouponStatus couponStatus = CouponStatus.ISSUED;

    @Min(0)
    @Max(100)
    @Column(name = "discount_rate")
    private Integer discountRate = 0;

    @Min(0)
    @Column(name = "discount_amount")
    private Integer discountAmount = 0;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

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

    public Coupon(String couponName, String couponContent, String couponType, Integer volume,
                  String couponCode, int discountRate, int discountAmount,
                  LocalDate validFrom, LocalDate validUntil,User createdBy) {
        this.name = couponName;
        this.content = couponContent;
        this.couponType = CouponType.valueOf(couponType);
        this.volume = volume;
        if(couponCode==null) { this.code = createCouponCode();} else { this.code = couponCode; }
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.createdBy = createdBy;
    }

    // createCouponToUser
    public Coupon(String couponName, String couponContent, String couponType,
                  String couponCode, int discountRate, int discountAmount,
                  LocalDate validFrom, LocalDate validUntil, User createdBy, User owner) {
        this.name = couponName;
        this.content = couponContent;
        this.couponType = CouponType.valueOf(couponType);
        this.volume = 1;
        this.code = couponCode;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.createdBy = createdBy;
        this.owner = owner;
        this.registeredAt = LocalDateTime.now();
        this.couponStatus = CouponStatus.REGISTERED;
    }

    // UUID 기반의 쿠폰 코드 생성 메서드
    public String createCouponCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    public Coupon(String code, String name, int discountAmount, LocalDate validFrom, LocalDate validUntil, CouponType couponType, String content) {
        this.code = code; // 추가
        this.name = name;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.couponType = couponType; // 추가
        this.content = content; // 추가
    }

    // 쿠폰 수정 메서드 추가
    public void updateCoupon(String code, String name, int discountAmount, LocalDate validFrom, LocalDate validUntil,
                             CouponType couponType, String content) {
        this.code = code;
        this.name = name;
        this.discountAmount = discountAmount;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.couponType = couponType;
        this.content = content;
    }

    public void deleteCoupon() {
        this.couponStatus = CouponStatus.DELETED;
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();  // 삭제 시점 저장
    }

    public void registerCoupon(User user) {
        this.owner = user;
        this.registeredAt = LocalDateTime.now();
        this.couponStatus = CouponStatus.REGISTERED;
    }

    public void redeemCoupon() {
        this.couponStatus= CouponStatus.REDEEMED;
    }

    public void addAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
    public void addEvent(Event event) {
        this.event = event;
    }

    public void updateCouponOwner(User Owner) {
        this.owner = Owner;
        this.registeredAt = LocalDateTime.now();
    }
}
