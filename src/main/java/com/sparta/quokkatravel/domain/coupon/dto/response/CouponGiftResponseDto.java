package com.sparta.quokkatravel.domain.coupon.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CouponGiftResponseDto {

    private final Long newOwnerId;
    private final LocalDateTime registerdAt;
}
