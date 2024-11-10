package com.sparta.quokkatravel.domain.coupon.entity;

import lombok.Getter;

@Getter
public enum CouponStatus {

    ACTIVATE,
    ISSUED,    // 발행 후
    REGISTERED,   // 유저에게 할당됨
    REDEEMED,  // 사용됨
    EXPIRED,   // 만료됨
    DELETED;  // 삭제됨

}
