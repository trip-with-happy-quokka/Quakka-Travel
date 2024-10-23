package com.sparta.quokkatravel.domain.room.controller;

import com.sparta.quokkatravel.domain.accommodation.dto.GuestAccommodationResponseDto;
import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.room.dto.GuestRoomResponseDto;
import com.sparta.quokkatravel.domain.room.service.GuestRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/guest")
@PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Room", description = "Guest 객실 관련 컨트롤러")
public class GuestRoomController {

    private final GuestRoomService guestRoomService;

    // 객실 전체 조회
    @GetMapping("/accommodations/{accommodationId}/rooms")
    @Operation(summary = "객실 전체 조회", description = "객실들을 조회하는 API")
    public ResponseEntity<?> getAllRooms(@PathVariable(name = "accommodationId") Long accomodationId,
                                         @RequestParam(required = false) Pageable pageable) {

        Page<GuestRoomResponseDto> rooms = guestRoomService.getAllRoom(accomodationId, pageable);
        return ResponseEntity.ok(ApiResponse.success("객실 조회 성공", rooms));
    }

    // 객실 단일 조회
    @GetMapping("/accommodations/{accommodationId}/rooms/{roomId}")
    @Operation(summary = "객실 전체 조회", description = "특정 객실 하나만 조회하는 API")
    public ResponseEntity<?> getAccommodation(@PathVariable(name = "accommodationId") Long accommodationId,
                                              @PathVariable(name = "roomId") Long roomId) {

        GuestRoomResponseDto guestRoomResponseDto = guestRoomService.getRoom(accommodationId, roomId);
        return ResponseEntity.ok(ApiResponse.success("객실 조회 성공", guestRoomResponseDto));
    }

}
