package com.sparta.quokkatravel.domain.payment.controller;

import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.payment.dto.PaymentConfirmRequestDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentCreateRequestDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import com.sparta.quokkatravel.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation/{reservationId}")
public class PaymentController {

    private final PaymentService paymentService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/payments")
    public ResponseEntity<?> createPayment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @PathVariable("reservationId") Long reservationId,
                                                            @RequestBody PaymentCreateRequestDto paymentCreateRequestDto) {
        PaymentResponseDto paymentResponseDto = paymentService.createPayment(userDetails, reservationId, paymentCreateRequestDto);

        return ResponseEntity.ok(ApiResponse.success("결제 요청 생성", paymentResponseDto));
    }

    @PostMapping("/payments/confirm")
    public ResponseEntity<?> confirmReservationPayment(@PathVariable("reservationId") String reservationId,
                                                       @RequestBody PaymentConfirmRequestDto paymentConfirmRequestDto) throws IOException {
        PaymentResponseDto paymentResponseDto = paymentService.confirmPayment(paymentConfirmRequestDto);

        return ResponseEntity.ok(ApiResponse.success("결제 승인 요청 성공", paymentResponseDto));
    }
}
