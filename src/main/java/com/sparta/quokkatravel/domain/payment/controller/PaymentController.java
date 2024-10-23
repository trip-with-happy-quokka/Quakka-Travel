package com.sparta.quokkatravel.domain.payment.controller;

import com.sparta.quokkatravel.domain.common.config.TossPaymentConfig;
import com.sparta.quokkatravel.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;

    // 결제 요청
    @PostMapping("/toss")
    public ResponseEntity<?> requsetTossPayment() {
        return ResponseEntity.ok().build();
    }

    // 결제 성공
    public ResponseEntity<?> tossPaymentSuccess() {
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
