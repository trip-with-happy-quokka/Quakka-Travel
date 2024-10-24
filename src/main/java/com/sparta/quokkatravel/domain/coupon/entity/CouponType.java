package com.sparta.quokkatravel.domain.coupon.entity;

import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CouponType {
    ACCOMMODATION, EVENT;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("유효하지 않은 CouponType"));
    }
}
