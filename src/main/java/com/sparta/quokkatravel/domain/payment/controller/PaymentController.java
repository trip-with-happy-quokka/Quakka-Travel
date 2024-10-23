package com.sparta.quokkatravel.domain.payment.controller;

import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.common.config.TossPaymentConfig;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.payment.dto.PaymentRequestDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import com.sparta.quokkatravel.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/reservation/{reservationId}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;

    // 결제 요청
    @PostMapping("/toss")
    public ResponseEntity<?> requestTossPayment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @PathVariable(name = "reservationId") Long reservationId,
                                                @RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto paymentResponseDto = paymentService.requestTossPayment(userDetails, reservationId, paymentRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Toss Payment Request", paymentResponseDto));
    }

    // 결제 성공
    @GetMapping("/toss/success")
    public ResponseEntity<?> tossPaymentSuccess(@RequestParam) {
        return ResponseEntity.ok().build();
    }

    // 결제 실패
    public ResponseEntity<?> tossPaymentFailure() {
        return ResponseEntity.ok().build();
    }

    // 결제 취소
    public ResponseEntity<?> tossPaymentCancelPoint() {
        return ResponseEntity.ok().build();
    }

    // 결제 내역 조회
    public ResponseEntity<?> getChargingHistory() {
        return ResponseEntity.ok().build();
    }
}
