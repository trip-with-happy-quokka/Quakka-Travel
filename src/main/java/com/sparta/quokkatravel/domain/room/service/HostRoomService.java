package com.sparta.quokkatravel.domain.room.service;

import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.room.dto.HostRoomResponseDto;
import com.sparta.quokkatravel.domain.room.dto.RoomRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HostRoomService {

    // 생성
    HostRoomResponseDto createRoom(CustomUserDetails userDetails, Long accommodationId, RoomRequestDto roomRequestDto);

    // 조회
    Page<HostRoomResponseDto> getAllRoom(CustomUserDetails userDetails, Long accommodationId, Pageable pageable);
    HostRoomResponseDto getRoom(CustomUserDetails userDetails, Long accommodationId, Long roomId);

    // 수정
    HostRoomResponseDto updateRoom(CustomUserDetails userDetails, Long roomId, RoomRequestDto roomRequestDto);

    // 삭제
    String deleteRoom(CustomUserDetails userDetails, Long accommodationId, Long roomId);
}
