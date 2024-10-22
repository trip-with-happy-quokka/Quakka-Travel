package com.sparta.quokkatravel.domain.admin.reservation.dto;

import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AdminReservationResponseDto {

    private Long reservationId;
    private Long userId;
    private Long accommodationId;
    private String reservationStatus;
    private LocalDateTime reservationDate;
    private BigDecimal totalPrice;
    private String userName;  // 유저 이름 정보 추가

    // Reservation 엔티티로부터 DTO 생성
    public AdminReservationResponseDto(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.userId = reservation.getUser().getId();
        this.accommodationId = reservation.getRoom().getId();
        this.reservationStatus = reservation.getStatus(); // status 필드 추가 필요
        this.reservationDate = reservation.getReservationDate(); // reservationDate 필드 추가 필요
        this.totalPrice = BigDecimal.valueOf(reservation.getTotalPrice()); // int를 BigDecimal로 변환
        this.userName = reservation.getUser().getName();  // 관리자가 보는 유저 이름
    }
}