package com.sparta.quokkatravel.domain.admin.reservation.dto;

import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
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
        this.reservationStatus = reservation.getStatus().name(); // Enum을 String으로 변환
        this.reservationDate = reservation.getCreatedAt(); // LocalDate를 LocalDateTime으로 변환
        this.totalPrice = BigDecimal.valueOf(reservation.getTotalPrice()); // int를 BigDecimal로 변환 (BigDecimal이 가격 계산에 더 효율적이라 판단)
        this.userName = reservation.getUser().getNickname();  // 관리자가 보는 유저 이름
    }
}