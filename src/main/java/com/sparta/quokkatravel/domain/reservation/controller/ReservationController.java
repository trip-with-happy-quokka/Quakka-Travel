package com.sparta.quokkatravel.domain.reservation.controller;

import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationRequestDto;
import com.sparta.quokkatravel.domain.reservation.dto.ReservationResponseDto;
import com.sparta.quokkatravel.domain.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/guest")
@PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Reservation", description = "예약 관련 컨트롤러")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    // 에약 작성
    @PostMapping("/rooms/{roomId}/reservations")
    public ResponseEntity<?> createReservation(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @PathVariable Long roomId,
                                               @RequestBody ReservationRequestDto reservationRequestDto) {
        ReservationResponseDto reservationResponseDto = reservationService.createReservation(userDetails.getEmail(), roomId, reservationRequestDto);
        log.info("email:{}", userDetails.getEmail());
        return ResponseEntity.ok(ApiResponse.created("예약 생성 성공", reservationResponseDto));
    }

    // 에약 전체 조회
    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservations(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @RequestParam(required = false) Pageable pageable) {

        Page<ReservationResponseDto> reservations = reservationService.getAllReservation(userDetails.getEmail(), pageable);
        return ResponseEntity.ok(ApiResponse.success("예약 전체 조회 성공", reservations));
    }

    // 예약 단건 조회
    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<?> getReservation(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable Long reservationId) {
        ReservationResponseDto reservationResponseDto = reservationService.getReservation(userDetails.getEmail(), reservationId);
        return ResponseEntity.ok(ApiResponse.success("예약 단건 조회 성공", reservationResponseDto));
    }

    // 예약 수정
    @PutMapping("/rooms/{roomId}/reservations/{reservationId}")
    public ResponseEntity<?> updateReservation(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @PathVariable Long roomId,
                                               @PathVariable Long reservationId,
                                               @RequestBody ReservationRequestDto reservationRequestDto) throws AccessDeniedException {
        ReservationResponseDto reservationResponseDto = reservationService.updateReservation(userDetails.getEmail(), roomId, reservationId, reservationRequestDto);
        return ResponseEntity.ok(ApiResponse.success("예약 수정 성공", reservationResponseDto));
    }

    // 예약 취소
    @DeleteMapping("/rooms/{roomId}/reservations/{reservationId}")
    public ResponseEntity<?> cancelReservation(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @PathVariable Long roomId,
                                               @PathVariable Long reservationId) throws AccessDeniedException {

        String cancelMessage = reservationService.cancelReservation(userDetails.getEmail(), roomId, reservationId);
        return ResponseEntity.ok(ApiResponse.success("예약 취소 성공", cancelMessage));
    }

}
