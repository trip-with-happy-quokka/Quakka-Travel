package com.sparta.quokkatravel.domain.accommodation.controller;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.HostAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.service.HostAccommodationService;
import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/host")
@PreAuthorize("hasRole('HOST')")
@RequiredArgsConstructor
@Tag(name = "Accommodation", description = "Host 숙소 관련 컨트롤러")
public class HostAccommodationController {

    private final HostAccommodationService hostAccommodationService;

    // 숙소 생성
    @PostMapping("/accommodations")
    @Operation(summary = "숙소 생성", description = "HOST 유저가 숙소를 생성하는 API")
    public ResponseEntity<?> createAccommodation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @RequestPart(name = "image", required = false) MultipartFile image,
                                                 @RequestPart(name = "dto") AccommodationRequestDto accommodationRequestDto) throws IOException {

        HostAccommodationResponseDto hostAccommodationResponseDto = hostAccommodationService.createAccommodation(customUserDetails, image, accommodationRequestDto);
        return ResponseEntity.ok(ApiResponse.created("숙소 생성 성공", hostAccommodationResponseDto));
    }

    // 숙소 전체 조회
    @GetMapping("/accommodations")
    @Operation(summary = "숙소 전체 조회", description = "HOST 유저의 숙소들을 조회하는 API")
    public ResponseEntity<?> getAllAccommodation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @RequestParam(required = false) Pageable pageable) {

        Page<HostAccommodationResponseDto> accommodations = hostAccommodationService.getAllAccommodation(customUserDetails, pageable);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", accommodations));
    }

    // 숙소 단일 조회
    @GetMapping("/accommodations/{accommodationId}")
    @Operation(summary = "숙소 전체 조회", description = "HOST 유저의 특정 숙소 하나만 조회하는 API")
    public ResponseEntity<?> getAccommodation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              @PathVariable(name = "accommodationId") Long accommodationId) {

        HostAccommodationResponseDto hostAccommodationResponseDto = hostAccommodationService.getAccommodation(customUserDetails, accommodationId);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", hostAccommodationResponseDto));
    }

    // 숙소 수정
    @PutMapping("/accommodations/{accommodationId}")
    @Operation(summary = "숙소 수정", description = "HOST 유저의 특정 숙소를 수정하는 API")
    public ResponseEntity<?> updateAccommodation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PathVariable(name = "accommodationId") Long accommodationId,
                                                 @RequestBody AccommodationRequestDto accommodationRequestDto) {

        HostAccommodationResponseDto hostAccommodationResponseDto = hostAccommodationService.updateAccommodation(customUserDetails, accommodationId, accommodationRequestDto);
        return ResponseEntity.ok(ApiResponse.success("숙소 수정 성공", hostAccommodationResponseDto));
    }

    // 숙소 삭제
    @DeleteMapping("/accommodations/{accommodationId}")
    @Operation(summary = "숙소 삭제", description = "HOST 유저의 특정 숙소를 삭제하는 API")
    public ResponseEntity<?> updateAccommodation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PathVariable(name = "accommodationId") Long accommodationId) {

        String deleteMessage = hostAccommodationService.deleteAccommodation(customUserDetails, accommodationId);
        return ResponseEntity.ok(ApiResponse.success("숙소 수정 성공", deleteMessage));
    }

}
