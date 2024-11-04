package com.sparta.quokkatravel.domain.room.controller;

import com.sparta.quokkatravel.domain.common.shared.ApiResponse;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.room.dto.HostRoomResponseDto;
import com.sparta.quokkatravel.domain.room.dto.RoomRequestDto;
import com.sparta.quokkatravel.domain.room.service.HostRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/host")
@PreAuthorize("hasRole('HOST')")
@RequiredArgsConstructor
@Tag(name = "Room", description = "Host 객실 관련 컨트롤러")
public class HostRoomController {

    private final HostRoomService hostRoomService;

    // 숙소 생성
    @PostMapping("/accommodations/{accommodationId}/rooms")
    @Operation(summary = "객실 생성", description = "HOST 유저가 객실을 생성하는 API")
    public ResponseEntity<?> createRoom(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PathVariable Long accommodationId,
                                                 @RequestBody RoomRequestDto roomRequestDto) {

        HostRoomResponseDto hostRoomResponseDto = hostRoomService.createRoom(customUserDetails, accommodationId, roomRequestDto);
        return ResponseEntity.ok(ApiResponse.created("객실 생성 성공", hostRoomResponseDto));
    }

    // 숙소 전체 조회
    @GetMapping("/accommodations/{accommodationId}/rooms")
    @Operation(summary = "객실 전체 조회", description = "HOST 유저의 객실들을 조회하는 API")
    public ResponseEntity<?> getAllAccommodation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PathVariable Long accommodationId,
                                                 @RequestParam(required = false, defaultValue = "0") int pageNo,
                                                 @RequestParam(required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<HostRoomResponseDto> rooms = hostRoomService.getAllRoom(customUserDetails, accommodationId, pageable);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", rooms));
    }

    // 숙소 단일 조회
    @GetMapping("/accommodations/{accommodationId}/rooms/{roomId}")
    @Operation(summary = "숙소 전체 조회", description = "HOST 유저의 특정 숙소 하나만 조회하는 API")
    public ResponseEntity<?> getAccommodation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              @PathVariable(name = "accommodationId") Long accommodationId,
                                              @PathVariable(name = "roomId") Long roomId) {

        HostRoomResponseDto hostRoomResponseDto = hostRoomService.getRoom(customUserDetails, accommodationId, roomId);
        return ResponseEntity.ok(ApiResponse.success("숙소 조회 성공", hostRoomResponseDto));
    }

    // 숙소 수정
    @PutMapping("/accommodations/{accommodationId}/rooms/{roomId}")
    @CachePut(value = "Room", key = "#roomId", cacheManager = "cacheManager")
    @Operation(summary = "숙소 수정", description = "HOST 유저의 특정 숙소를 수정하는 API")
    public ResponseEntity<?> updateAccommodation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PathVariable(name = "roomId") Long roomId,
                                                 @RequestBody RoomRequestDto roomRequestDto) {

        HostRoomResponseDto hostRoomResponseDto = hostRoomService.updateRoom(customUserDetails, roomId, roomRequestDto);
        return ResponseEntity.ok(ApiResponse.success("숙소 수정 성공", hostRoomResponseDto));
    }

    // 숙소 삭제
    @DeleteMapping("/accommodations/{accommodationId}/rooms/{roomId}")
    @CacheEvict(value = "Room", key = "#roomId", cacheManager = "cacheManager")
    @Operation(summary = "숙소 삭제", description = "HOST 유저의 특정 숙소를 삭제하는 API")
    public ResponseEntity<?> updateAccommodation(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PathVariable(name = "accommodationId") Long accommodationId) {

        String deleteMessage = hostRoomService.deleteRoom(customUserDetails, accommodationId);
        return ResponseEntity.ok(ApiResponse.success("숙소 수정 성공", deleteMessage));
    }
}
