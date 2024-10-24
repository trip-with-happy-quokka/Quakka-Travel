package com.sparta.quokkatravel.domain.payment.dto;

import lombok.Getter;

@Getter
public class PaymentConfirmRequestDto {

    private String paymentKey;
    private String orderId;
    private String amount;

    public PaymentConfirmRequestDto(String paymentKey, String orderId, String amount) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
    }
}
