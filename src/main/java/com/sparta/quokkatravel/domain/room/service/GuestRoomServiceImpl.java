package com.sparta.quokkatravel.domain.room.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.room.dto.GuestRoomResponseDto;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuestRoomServiceImpl implements GuestRoomService {

    private final RoomRepository roomRepository;
    private final AccommodationRepository accommodationRepository;

    @Override
    @Cacheable(value = "Room", key = "#roomId + '_' + #userId", cacheManager = "cacheManager")
    public GuestRoomResponseDto getRoom(Long userId, Long accommodationId, Long roomId) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation Not Found"));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("Room Not Found"));

        if (!Objects.equals(room.getAccommodation().getId(), accommodation.getId())) {
            throw new NotFoundException("해당 숙소에는 그런 객실이 없습니다.");
        }


        return new GuestRoomResponseDto(room);
    }

    @Override
    public Page<GuestRoomResponseDto> getAllRoom(Long accommodationId, Pageable pageable) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation Not Found"));

        return roomRepository.findByAccommodation(accommodation, pageable)
                .map(GuestRoomResponseDto::new);
    }
}
