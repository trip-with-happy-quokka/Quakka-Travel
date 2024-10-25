package com.sparta.quokkatravel.domain.reservation.service;

import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationRequestDto;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

public interface ReservationService {

    // 예약 생성
    ReservationResponseDto createReservation(CustomUserDetails userDetails, Long roomId, ReservationRequestDto reservationRequestDto);


    // 예약 조회
    ReservationResponseDto getReservation(CustomUserDetails userDetails, Long id);
    Page<ReservationResponseDto> getAllReservation(CustomUserDetails userDetails, Pageable pageable);

    // 예약 수정
    ReservationResponseDto updateReservation(CustomUserDetails userDetails, Long id, ReservationRequestDto reservationRequestDto) throws AccessDeniedException;

    // 예약 삭제
    String cancelReservation(CustomUserDetails userDetails, Long id) throws AccessDeniedException;
}
