package com.sparta.quokkatravel.domain.payment.dto;

import com.sparta.quokkatravel.domain.payment.entity.Payment;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargingHistoryDto {
    private Long paymentHistoryId;
    @NonNull
    private Long amount;
    @NonNull
    private String orderName;

    private boolean isPaySuccessYN;
    private LocalDateTime createdAt;

    public ChargingHistoryDto(Payment payment) {
        this.paymentHistoryId = payment.getPaymentId();
        this.amount = payment.getAmount();
        this.orderName = payment.getOrderName();
        this.isPaySuccessYN = payment.isPaySuccessYN();
        this.createdAt = payment.getCreatedAt();
    }
}
