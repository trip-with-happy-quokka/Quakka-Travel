package com.sparta.quokkatravel.domain.payment.dto;

import com.sparta.quokkatravel.domain.payment.entity.PayType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PaymentRequestDto {

    private PayType payType;
    private Long amount;
    private Long reservationId;
    private String successUrl;
    private String failUrl;

    public PaymentRequestDto(PayType payType, Long amount, Long reservationId, String successUrl, String failUrl) {
        this.payType = payType;
        this.amount = amount;
        this.reservationId = reservationId;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
    }
}
