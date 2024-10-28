package com.sparta.quokkatravel.domain.room.service;

import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.room.dto.HostRoomResponseDto;
import com.sparta.quokkatravel.domain.room.dto.RoomRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HostRoomService {

    // 생성
    HostRoomResponseDto createRoom(String email, Long accommodationId, RoomRequestDto roomRequestDto);

    // 조회
    HostRoomResponseDto getRoom(String email, Long accommodationId, Long roomId);
    Page<HostRoomResponseDto> getAllRoom(String email, Long accommodationId, Pageable pageable);

    // 수정
    HostRoomResponseDto updateRoom(String email, Long roomId, RoomRequestDto roomRequestDto);

    // 삭제
    String deleteRoom(String email, Long roomId);
}
