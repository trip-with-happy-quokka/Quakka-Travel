package com.sparta.quokkatravel.domain.payment.service;

import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.payment.dto.PaymentRequestDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentSuccessResponseDto;
import com.sparta.quokkatravel.domain.payment.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PaymentService {

    PaymentResponseDto requestTossPayment(CustomUserDetails userDetails, Long reservationId, PaymentRequestDto paymentRequestDto);
    PaymentSuccessResponseDto tossPaymentSuccess(PaymentRequestDto paymentRequestDto);
    PaymentSuccessResponseDto requestPaymentAccept(PaymentRequestDto paymentRequestDto);
    Slice<Payment> findAllChargingHistories(CustomUserDetails userDetails, Pageable pageable);
    PaymentResponseDto verifyPayment(PaymentRequestDto paymentRequestDto);

}
