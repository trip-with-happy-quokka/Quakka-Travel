package com.sparta.quokkatravel.domain.payment.dto;

import com.sparta.quokkatravel.domain.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentConfirmRequestDto {

    private String paymentKey;
    private String orderId;
    private int amount;

    public PaymentConfirmRequestDto(Payment payment) {
        this.paymentKey = payment.getPaymentKey();
        this.orderId = payment.getId().toString();
        this.amount = (int) payment.getAmount();
    }
}
