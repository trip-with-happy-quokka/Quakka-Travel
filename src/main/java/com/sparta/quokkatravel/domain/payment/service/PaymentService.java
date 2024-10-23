package com.sparta.quokkatravel.domain.payment.service;

import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.payment.dto.PaymentRequestDto;
import com.sparta.quokkatravel.domain.payment.entity.Payment;

public interface PaymentService {

    // 결제 요청
    Payment requestPayment(CustomUserDetails userDetails, PaymentRequestDto requestDto);

}
