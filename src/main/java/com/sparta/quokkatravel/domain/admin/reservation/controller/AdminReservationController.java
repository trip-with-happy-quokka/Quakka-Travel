package com.sparta.quokkatravel.domain.admin.reservation.controller;

import com.sparta.quokkatravel.domain.admin.reservation.dto.AdminReservationResponseDto;
import com.sparta.quokkatravel.domain.admin.reservation.service.AdminReservationService;
import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/admin/reservations")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "AdminReservation", description = "Admin 예약 관련 컨트롤러")
public class AdminReservationController {

    private final AdminReservationService adminReservationService;

    // 모든 예약 목록 조회
    @GetMapping
    @Operation(summary = "예약 목록 조회", description = "관리자가 모든 예약 목록을 조회하는 API")
    public ResponseEntity<?> getAllReservations() {
        List<AdminReservationResponseDto> reservations = adminReservationService.getAllReservations();
        return ResponseEntity.ok(ApiResponse.success("예약 목록 조회 성공", reservations));
    }

    // 특정 예약 상세 조회
    @GetMapping("/{reservationId}")
    @Operation(summary = "예약 상세 조회", description = "관리자가 특정 예약의 상세 정보를 조회하는 API")
    public ResponseEntity<?> getReservationById(@PathVariable Long reservationId) {
        AdminReservationResponseDto reservation = adminReservationService.getReservationById(reservationId);
        return ResponseEntity.ok(ApiResponse.success("예약 상세 조회 성공", reservation));
    }

    // 예약 삭제
    @DeleteMapping("/{reservationId}")
    @Operation(summary = "예약 삭제", description = "관리자가 특정 예약을 삭제하는 API")
    public ResponseEntity<?> deleteReservation(@PathVariable Long reservationId) {
        adminReservationService.deleteReservation(reservationId);
        return ResponseEntity.ok(ApiResponse.success("예약 삭제 성공"));
    }
}
