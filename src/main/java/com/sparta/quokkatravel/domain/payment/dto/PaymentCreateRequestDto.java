package com.sparta.quokkatravel.domain.payment.dto;

import com.sparta.quokkatravel.domain.payment.entity.PayStatus;
import com.sparta.quokkatravel.domain.payment.entity.PayType;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentCreateRequestDto {

    private PayType payType; // 결제 수단 CARD, BANK

    public PaymentCreateRequestDto(PayType payType) {
        this.payType = payType;
    }
}
