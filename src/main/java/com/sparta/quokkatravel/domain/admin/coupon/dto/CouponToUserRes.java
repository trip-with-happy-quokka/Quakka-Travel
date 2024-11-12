package com.sparta.quokkatravel.domain.admin.coupon.dto;

import lombok.Getter;

@Getter
public class CouponToUserRes {
    private final String couponName;
    private final String couponContents;

    public CouponToUserRes(String couponName, String couponContents) {
        this.couponName = couponName;
        this.couponContents = couponContents;
    }
}
