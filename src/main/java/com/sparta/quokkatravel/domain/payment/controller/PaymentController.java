package com.sparta.quokkatravel.domain.payment.controller;

import com.sparta.quokkatravel.domain.common.config.TossPaymentConfig;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import com.sparta.quokkatravel.domain.payment.dto.ChargingHistoryDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentFailDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import com.sparta.quokkatravel.domain.payment.mapper.PaymentMapper;
import com.sparta.quokkatravel.domain.payment.service.PaymentServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentServiceImpl paymentService;
    private final TossPaymentConfig tossPaymentConfig;
    private final PaymentMapper mapper;

    public PaymentController(PaymentServiceImpl paymentService, TossPaymentConfig tossPaymentConfig, PaymentMapper mapper) {
        this.paymentService = paymentService;
        this.tossPaymentConfig = tossPaymentConfig;
        this.mapper = mapper;
    }

    @PostMapping("/toss")
    public ResponseEntity<?> requestTossPayment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @RequestParam Long reservationId) {

        PaymentResponseDto paymentResDto = paymentService.requestTossPayment(userDetails.getUsername(), reservationId);
        paymentResDto.setSuccessUrl(tossPaymentConfig.getSuccessUrl());
        paymentResDto.setFailUrl(tossPaymentConfig.getFailUrl());

        return ResponseEntity.ok().body(paymentResDto);
    }
    @GetMapping("/toss/success")
    public ResponseEntity<?> tossPaymentSuccess(@RequestParam String paymentKey,
                                             @RequestParam String orderId,
                                             @RequestParam Long amount) {

        return ResponseEntity.ok().body(paymentService.tossPaymentSuccess(paymentKey, orderId, amount));
    }

    @GetMapping("/toss/fail")
    public ResponseEntity<?> tossPaymentFail(@RequestParam String code,
                                          @RequestParam String message,
                                          @RequestParam String orderId) {

        paymentService.tossPaymentFail(code, message, orderId);

        return ResponseEntity.ok().body(
                PaymentFailDto.builder()
                        .errorCode(code)
                        .errorMessage(message)
                        .orderId(orderId)
                        .build()
        );
    }

    @PostMapping("/toss/cancel")
    public ResponseEntity<?> tossPaymentCancelPoint(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @RequestParam String paymentKey,
                                                    @RequestParam String cancelReason) {
        Map map = paymentService.cancelPayment(userDetails.getUserId(), paymentKey, cancelReason);
        return ResponseEntity.ok(ApiResponse.success("cancel payment", map));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getChargingHistory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @RequestParam(required = false, defaultValue = "0") int pageNo,
                                                @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ChargingHistoryDto> chargingHistories = paymentService.findAllChargingHistories(userDetails.getUsername(), pageable);
        return ResponseEntity.ok(ApiResponse.success("charging history", chargingHistories));
    }
}