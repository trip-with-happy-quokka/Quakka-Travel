package com.sparta.quokkatravel.domain.accommodation.controller;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.service.AccommodationSerivceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Accommodation", description = "유저 컨트롤러")
public class AccommodationController {

    private final AccommodationSerivceImpl accommodationSerivceImpl;

    @PostMapping("/accommodations")
    @Operation(summary = "숙소 생성", description = "HOST 유저가 숙소를 생성하는 API")
    public ResponseEntity<?> createAccommodation(@RequestBody AccommodationRequestDto accommodationRequestDto) {
        AccommodationResponseDto accommodationResponseDto = accommodationSerivceImpl.createAccommodation(accommodationRequestDto);
        return ResponseEntity.ok(accommodationResponseDto);
    }
}
