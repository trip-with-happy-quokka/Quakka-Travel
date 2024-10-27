package com.sparta.quokkatravel.domain.payment.service;

import com.sparta.quokkatravel.domain.payment.dto.PaymentSuccessDto;
import com.sparta.quokkatravel.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PaymentService {
    Payment requestTossPayment(Payment payment, String userEmail);
    PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount);
    PaymentSuccessDto requestPaymentAccept(String paymentKey, String orderId, Long amount);
    Page<Payment> findAllChargingHistories(String username, Pageable pageable);
    Payment verifyPayment(String orderId, Long amount);
}
