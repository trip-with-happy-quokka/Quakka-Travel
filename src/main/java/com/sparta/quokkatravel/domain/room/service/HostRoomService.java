package com.sparta.quokkatravel.domain.room.service;

import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.room.dto.HostRoomResponseDto;
import com.sparta.quokkatravel.domain.room.dto.RoomRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HostRoomService {

    // 생성
    HostRoomResponseDto createRoom(CustomUserDetails userDetails, Long accommodationId, RoomRequestDto roomRequestDto);

    // 조회
    HostRoomResponseDto getRoom(CustomUserDetails customUserDetails, Long accommodationId, Long roomId);
    Page<HostRoomResponseDto> getAllRoom(CustomUserDetails customUserDetails, Long accommodationId, Pageable pageable);

    // 수정
    HostRoomResponseDto updateRoom(CustomUserDetails customUserDetails, Long roomId, RoomRequestDto roomRequestDto);

    // 삭제
    String deleteRoom(CustomUserDetails customUserDetails, Long roomId);
}
