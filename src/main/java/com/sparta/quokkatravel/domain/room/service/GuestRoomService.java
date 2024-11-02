package com.sparta.quokkatravel.domain.room.service;

import com.sparta.quokkatravel.domain.room.dto.GuestRoomResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuestRoomService {

    // 객실 단건 조회
    GuestRoomResponseDto getRoom(Long userId, Long accommodationId, Long roomId);

    // 객실 전체 조회
    Page<GuestRoomResponseDto> getAllRoom(Long accommodationId, Pageable pageable);
}
