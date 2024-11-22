package com.sparta.quokkatravel.domain.admin.reservation.service;

import com.sparta.quokkatravel.domain.admin.reservation.dto.AdminReservationResponseDto;
import com.sparta.quokkatravel.domain.admin.reservation.repository.AdminReservationRepository;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminReservationService {

    private final AdminReservationRepository adminReservationRepository;

    public AdminReservationService(AdminReservationRepository adminReservationRepository) {
        this.adminReservationRepository = adminReservationRepository;
    }

    // 모든 예약 조회
    public List<AdminReservationResponseDto> getAllReservations() {
        List<Reservation> reservations = adminReservationRepository.findAll();
        return reservations.stream()
                .map(AdminReservationResponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 예약 상세 조회
    public AdminReservationResponseDto getReservationById(Long reservationId) {
        Reservation reservation = adminReservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("예약을 찾을 수 없습니다."));
        return new AdminReservationResponseDto(reservation);
    }

    // 예약 삭제
    public void deleteReservation(Long reservationId) {
        Reservation reservation = adminReservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("예약을 찾을 수 없습니다."));
        adminReservationRepository.delete(reservation);
    }
}
