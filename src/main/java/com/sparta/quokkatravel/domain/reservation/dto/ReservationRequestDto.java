package com.sparta.quokkatravel.domain.reservation.dto;

import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.entity.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfGuests;

    public ReservationRequestDto(LocalDate startDate, LocalDate endDate, int numberOfGuests, int totalPrice, LocalDate reservationDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
    }
}
