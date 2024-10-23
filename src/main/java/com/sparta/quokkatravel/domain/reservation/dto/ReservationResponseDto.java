package com.sparta.quokkatravel.domain.reservation.dto;

import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.reservation.entity.ReservationStatus;
import com.sparta.quokkatravel.domain.user.entity.User;

import java.time.LocalDate;

public class ReservationResponseDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfGuests;
    private int totalPrice;
    private ReservationStatus status;
    private LocalDate reservationDate;
    private User user;
    private Room room;

    public ReservationResponseDto(Reservation reservation) {
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.numberOfGuests = reservation.getNumberOfGuests();
        this.totalPrice = reservation.getTotalPrice();
        this.status = reservation.getStatus();
        this.reservationDate = reservation.getReservationDate();
        this.user = reservation.getUser();
        this.room = reservation.getRoom();
    }
}
