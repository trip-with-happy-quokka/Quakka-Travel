package com.sparta.quokkatravel.domain.event.controller;

import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import com.sparta.quokkatravel.domain.event.dto.request.EventRequestDto;
import com.sparta.quokkatravel.domain.event.dto.response.EventResponseDto;
import com.sparta.quokkatravel.domain.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Event", description = "행사 관련 컨트롤러")
public class EventController {

    private final EventService eventService;

    @PostMapping("/events")
    @Operation(summary = "행사 생성", description = "행사 생성하는 API")
    public ResponseEntity<?> createEvent(
            @Valid @RequestBody EventRequestDto eventRequestDto) {

        EventResponseDto eventResponseDto = eventService.createEvent(eventRequestDto);
        return ResponseEntity.ok(ApiResponse.created("행사 생성 성공", eventResponseDto));
    }
}
