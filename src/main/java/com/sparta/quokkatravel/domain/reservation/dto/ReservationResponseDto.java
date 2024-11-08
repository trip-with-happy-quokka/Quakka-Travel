package com.sparta.quokkatravel.domain.reservation.dto;

import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.entity.ReservationStatus;
import com.sparta.quokkatravel.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReservationResponseDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private Long numberOfGuests;
    private Long totalPrice;
    private ReservationStatus status;
    private LocalDate reservationDate;

    private UserDto user;
    private RoomDto room;

    public ReservationResponseDto(Reservation reservation) {
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.numberOfGuests = reservation.getNumberOfGuests();
        this.totalPrice = reservation.getTotalPrice();
        this.status = reservation.getStatus();
        this.reservationDate = reservation.getCreatedAt() != null ? reservation.getCreatedAt().toLocalDate() : LocalDate.now();
        this.user = new UserDto(reservation.getUser());
        this.room = new RoomDto(reservation.getRoom());
    }
}
