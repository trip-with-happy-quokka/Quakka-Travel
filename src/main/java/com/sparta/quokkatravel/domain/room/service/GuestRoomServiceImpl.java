package com.sparta.quokkatravel.domain.room.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.room.dto.GuestRoomResponseDto;
import com.sparta.quokkatravel.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestRoomServiceImpl implements GuestRoomService {

    private final RoomRepository roomRepository;
    private final AccommodationRepository accommodationRepository;

    @Override
    public GuestRoomResponseDto getRoom(Long accommodationId, Long roomId) {
        return new GuestRoomResponseDto(roomRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Room Not Found")));
    }

    @Override
    public Page<GuestRoomResponseDto> getAllRoom(Long accommodationId, Pageable pageable) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation Not Found"));

        return roomRepository.findByAccommodation(accommodation, pageable)
                .map(GuestRoomResponseDto::new);
    }
}
