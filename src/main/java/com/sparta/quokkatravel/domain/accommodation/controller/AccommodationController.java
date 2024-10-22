package com.sparta.quokkatravel.domain.accommodation.controller;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.service.AccommodationSerivceImpl;
import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Accommodation", description = "숙소 관련 컨트롤러")
public class AccommodationController {

    private final AccommodationSerivceImpl accommodationSerivceImpl;

    @PostMapping("/accommodations")
    @Operation(summary = "숙소 생성", description = "HOST 유저가 숙소를 생성하는 API")
    public ResponseEntity<?> createAccommodation(@RequestBody AccommodationRequestDto accommodationRequestDto) {
        AccommodationResponseDto accommodationResponseDto = accommodationSerivceImpl.createAccommodation(accommodationRequestDto);
        return ResponseEntity.ok(ApiResponse.created("숙소 생성 성공", accommodationResponseDto));
    }

    @GetMapping("/accommodations")
    @Operation(summary = "숙소 전체 조회", description = "HOST 유저의 숙소들을 조회하는 API")
    public ResponseEntity<?> getAllAccommodation(@RequestBody AccommodationRequestDto accommodationRequestDto) {
        AccommodationResponseDto accommodationResponseDto = accommodationSerivceImpl.createAccommodation(accommodationRequestDto);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", accommodationResponseDto));
    }

    @GetMapping("/accommodations/{accommodationId}")
    @Operation(summary = "숙소 전체 조회", description = "HOST 유저의 특정 숙소 하나만 조회하는 API")
    public ResponseEntity<?> getAccommodation(@RequestBody AccommodationRequestDto accommodationRequestDto) {
        AccommodationResponseDto accommodationResponseDto = accommodationSerivceImpl.createAccommodation(accommodationRequestDto);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", accommodationResponseDto));
    }
}
