package com.sparta.quokkatravel.domain.payment.dto;

import com.sparta.quokkatravel.domain.payment.entity.Payment;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long amount; // 가격 정보

    private String orderName; // 주문명

    private String yourSuccessUrl; // 성공 시 리다이렉트 될 URL
    private String yourFailUrl; // 실패 시 리다이렉트 될 URL

    public Payment toEntity() {
        return Payment.builder()
                .amount(amount)
                .orderName(orderName)
                .orderId(UUID.randomUUID().toString())
                .paySuccessYN(false)
                .build();
    }
}