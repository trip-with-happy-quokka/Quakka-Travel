package com.sparta.quokkatravel.domain.payment.service;

import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.payment.dto.PaymentConfirmRequestDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentCreateRequestDto;
import com.sparta.quokkatravel.domain.payment.dto.PaymentResponseDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PaymentService {
    PaymentResponseDto createPayment(CustomUserDetails userDetails, Long reservationId, PaymentCreateRequestDto paymentCreateRequestDto);
    PaymentResponseDto confirmPayment(Long paymentId) throws IOException;
    PaymentResponseDto approvePayment(String paymentKey, String orderId, int amount) throws Exception;
}
