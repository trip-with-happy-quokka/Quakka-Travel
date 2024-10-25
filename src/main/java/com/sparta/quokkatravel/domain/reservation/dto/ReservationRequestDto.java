package com.sparta.quokkatravel.domain.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private Long numberOfGuests;
    private String couponCode;

    public ReservationRequestDto(LocalDate startDate, LocalDate endDate, Long numberOfGuests, String couponCode) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.couponCode = couponCode;
    }
}
