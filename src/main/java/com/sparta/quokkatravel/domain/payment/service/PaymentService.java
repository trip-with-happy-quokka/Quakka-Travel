package com.sparta.quokkatravel.domain.payment.service;

import com.sparta.quokkatravel.domain.payment.dto.ChargingHistoryDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentSuccessDto;
import com.sparta.quokkatravel.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    PaymentResponseDto requestTossPayment(String userEmail, Long reservationId);
    PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount);
    PaymentSuccessDto requestPaymentAccept(String paymentKey, String orderId, Long amount);
    Page<ChargingHistoryDto> findAllChargingHistories(String username, Pageable pageable);
    Payment verifyPayment(String orderId, Long amount);
}
