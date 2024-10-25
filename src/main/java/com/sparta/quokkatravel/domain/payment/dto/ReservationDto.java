package com.sparta.quokkatravel.domain.payment.dto;

import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationDto {

    private Long id;
    private LocalDate reservationDate;

    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.reservationDate = reservation.getCreatedAt().toLocalDate();
    }
}
