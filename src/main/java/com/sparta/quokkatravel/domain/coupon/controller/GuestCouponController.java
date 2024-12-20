package com.sparta.quokkatravel.domain.coupon.controller;

import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponCodeRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponGiftRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.*;
import com.sparta.quokkatravel.domain.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Coupon", description = "쿠폰 관련 컨트롤러")
public class GuestCouponController {

    private final CouponService couponService;

    @PutMapping("/users/{userId}/coupons")
    @Operation(summary = "쿠폰 등록", description = "유저가 쿠폰 번호를 등록해서 본인 쿠폰으로 만드는 API")
    public ResponseEntity<?> registerCoupon(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId,
            @Valid @RequestBody CouponCodeRequestDto couponCodeRequestDto) {

        CouponCodeResponseDto couponCodeResponseDto = couponService.registerCoupon(customUserDetails.getEmail(), userId, couponCodeRequestDto);
        return ResponseEntity.ok(ApiResponse.created("쿠폰 등록 성공", couponCodeResponseDto));
    }

    @PutMapping("/users/{userId}/coupons/{couponId}/gifts")
    @Operation(summary = "쿠폰 선물하기", description = "다른 유저에게 쿠폰을 선물하는 API")
    public ResponseEntity<?> giveCouponToOther(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId,
            @PathVariable Long couponId,
            @Valid @RequestBody CouponGiftRequestDto couponGiftRequestDto
    ) {
        CouponGiftResponseDto couponGiftResponseDto = couponService.giveCouponToOther(
                customUserDetails.getEmail(), userId, couponId, couponGiftRequestDto);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 선물 성공", couponGiftResponseDto));
    }

    @PutMapping("/users/{userId}/coupons/{couponId}")
    @Operation(summary = "쿠폰 사용", description = "쿠폰을 사용하는 API")
    public ResponseEntity<?> redeemCoupon(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId,
            @PathVariable Long couponId) {

        CouponRedeemResponseDto couponRedeemResponseDto = couponService.redeemCoupon(customUserDetails.getEmail(), userId, couponId);
        return ResponseEntity.ok(ApiResponse.created("쿠폰 등록 성공", couponRedeemResponseDto));
    }

    @GetMapping("/users/{userId}/coupons")
    @Operation(summary = "내 쿠폰 조회", description = "내 쿠폰 전체 조회 API")
    public ResponseEntity<?> getAllMyCoupons(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId) {

        List<CouponResponseDto> coupons = couponService.getAllMyCoupons(customUserDetails.getEmail(), userId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 전체 조회 성공", coupons));
    }

    @PutMapping("/coupons/{couponId}")
    @Operation(summary = "쿠폰 삭제", description = "쿠폰 삭제(Soft Delete) API")
    public ResponseEntity<?> deleteCoupon(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long couponId) {
        CouponDeleteResponseDto couponDeleteResponseDto = couponService.deleteCoupon(customUserDetails.getEmail(), couponId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 삭제 성공", couponDeleteResponseDto));
    }
}
