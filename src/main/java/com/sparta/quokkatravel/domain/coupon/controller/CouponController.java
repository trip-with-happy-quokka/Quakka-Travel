package com.sparta.quokkatravel.domain.coupon.controller;

import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponCodeRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.request.CouponRequestDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponCodeResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponDeleteResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponRedeemResponseDto;
import com.sparta.quokkatravel.domain.coupon.dto.response.CouponResponseDto;
import com.sparta.quokkatravel.domain.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Coupon", description = "쿠폰 관련 컨트롤러")
public class CouponController {

    private final CouponService couponService;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/admin/events/{eventId}/coupons")
    @Operation(summary = "행사 쿠폰 발행", description = "관리자 권한으로 쿠폰을 발행하는 API")
    public ResponseEntity<?> createEventCoupon(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long eventId,
            @Valid @RequestBody CouponRequestDto couponRequestDto) {

        CouponResponseDto couponResponseDto = couponService.createEventCoupon(customUserDetails, eventId, couponRequestDto);
        return ResponseEntity.ok(ApiResponse.created("행사 쿠폰 발급 성공", couponResponseDto));
    }

    @PostMapping("/admin/accommodations/{accommodationId}/coupons")
    @Operation(summary = "숙소 쿠폰 발행", description = "관리자 권한으로 쿠폰을 발행하는 API")
    public ResponseEntity<?> createAccommodationCoupon(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long accommodationId,
            @Valid @RequestBody CouponRequestDto couponRequestDto) {

        CouponResponseDto couponResponseDto = couponService.createAccommodationCoupon(customUserDetails, accommodationId, couponRequestDto);
        return ResponseEntity.ok(ApiResponse.created("숙소 쿠폰 발급 성공", couponResponseDto));
    }

    @PutMapping("/users/{userId}/coupons")
    @Operation(summary = "쿠폰 등록", description = "유저가 쿠폰 번호를 등록해서 본인 쿠폰으로 만드는 API")
    public ResponseEntity<?> registerCoupon(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId,
            @Valid @RequestBody CouponCodeRequestDto couponCodeRequestDto) {

        CouponCodeResponseDto couponCodeResponseDto = couponService.registerCoupon(customUserDetails, userId, couponCodeRequestDto);
        return ResponseEntity.ok(ApiResponse.created("쿠폰 등록 성공", couponCodeResponseDto));
    }

    @PutMapping("/users/{userId}/coupons/{couponId}")
    @Operation(summary = "쿠폰 사용", description = "쿠폰을 사용하는 API")
    public ResponseEntity<?> redeemCoupon(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId,
            @PathVariable Long couponId) {

        CouponRedeemResponseDto couponRedeemResponseDto = couponService.redeemCoupon(customUserDetails, userId, couponId);
        return ResponseEntity.ok(ApiResponse.created("쿠폰 등록 성공", couponRedeemResponseDto));
    }

    @GetMapping("/users/{userId}/coupons")
    @Operation(summary = "내 쿠폰 조회", description = "내 쿠폰 전체 조회 API")
    public ResponseEntity<?> getAllMyCoupons(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId) {

        List<CouponResponseDto> coupons = couponService.getAllMyCoupons(customUserDetails, userId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 전체 조회 성공", coupons));
    }

    @PutMapping("/coupons/{couponId}")
    @Operation(summary = "쿠폰 삭제", description = "쿠폰 삭제(Soft Delete) API")
    public ResponseEntity<?> deleteCoupon(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long couponId) {
        CouponDeleteResponseDto couponDeleteResponseDto = couponService.deleteCoupon(customUserDetails, couponId);
        return ResponseEntity.ok(ApiResponse.success("쿠폰 삭제 성공", couponDeleteResponseDto));
    }
}
