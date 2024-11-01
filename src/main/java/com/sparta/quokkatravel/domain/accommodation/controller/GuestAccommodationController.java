package com.sparta.quokkatravel.domain.accommodation.controller;

import com.sparta.quokkatravel.domain.accommodation.dto.GuestAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.service.GuestAccommodationServiceImpl;
import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/guest")
@PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Accommodation", description = "Guest 숙소 관련 컨트롤러")
public class GuestAccommodationController {

    private final GuestAccommodationServiceImpl guestAccommodationServiceImpl;

    // 숙소 전체 조회
    @GetMapping("/accommodations")
    @Operation(summary = "숙소 전체 조회", description = "숙소들을 조회하는 API")
    public ResponseEntity<?> getAllAccommodation(@RequestParam(required = false) Pageable pageable) {

        Page<GuestAccommodationResponseDto> accommodations = guestAccommodationServiceImpl.getAllAccommodation(pageable);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", accommodations));
    }

    // 숙소 단일 조회
    @GetMapping("/accommodations/{accommodationId}")
    @Operation(summary = "숙소 단건 조회", description = "특정 숙소 하나만 조회하는 API")
    public ResponseEntity<?> getAccommodation(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @PathVariable(name = "accommodationId") Long accommodationId) {

        GuestAccommodationResponseDto guestAccommodationResponseDto = guestAccommodationServiceImpl.getAccommodation(userDetails.getUserId(), accommodationId);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", guestAccommodationResponseDto));
    }
}
