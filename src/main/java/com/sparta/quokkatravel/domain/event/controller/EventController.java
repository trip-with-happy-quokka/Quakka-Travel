package com.sparta.quokkatravel.domain.event.controller;

import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import com.sparta.quokkatravel.domain.event.dto.request.EventRequestDto;
import com.sparta.quokkatravel.domain.event.dto.response.EventResponseDto;
import com.sparta.quokkatravel.domain.event.service.EventServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Event", description = "행사 관련 컨트롤러")
@PreAuthorize("hasRole('ADMIN')")
public class EventController {

    private final EventServiceImpl eventService;

    @PostMapping("/events")
    @Operation(summary = "행사 생성", description = "행사 생성하는 API")
    public ResponseEntity<?> createEvent(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody EventRequestDto eventRequestDto) {

        EventResponseDto eventResponseDto = eventService.createEvent(customUserDetails.getEmail() , eventRequestDto);
        return ResponseEntity.ok(ApiResponse.created("행사 생성 성공", eventResponseDto));
    }

    @GetMapping("/events")
    @Operation(summary = "행사 전체 조회", description = "행사 조회하는 API")
    public ResponseEntity<?> getAllEvents(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {

        List<EventResponseDto> eventResponseDtoList = eventService.getAllEvents(customUserDetails.getEmail());
        return ResponseEntity.ok(ApiResponse.success("행사 전체 조회 성공", eventResponseDtoList));
    }

    @PutMapping("/events/{eventId}")
    @Oper



}
