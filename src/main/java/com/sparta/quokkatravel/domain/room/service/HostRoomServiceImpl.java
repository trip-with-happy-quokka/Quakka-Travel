package com.sparta.quokkatravel.domain.room.service;

import com.sparta.quokkatravel.domain.accommodation.dto.GuestAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.dto.HostAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.room.dto.HostRoomResponseDto;
import com.sparta.quokkatravel.domain.room.dto.RoomRequestDto;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.room.repository.RoomRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostRoomServiceImpl implements HostRoomService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final AccommodationRepository accommodationRepository;

    @Override
    public HostRoomResponseDto createRoom(CustomUserDetails userDetails, RoomRequestDto roomRequestDto) {
        Room room = new Room(roomRequestDto);
        roomRepository.save(room);
        return new HostRoomResponseDto(room);
    }

    @Override
    public HostRoomResponseDto getRoom(Long accommodationId, Long roomId) {
        return new HostRoomResponseDto(roomRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Room Not Found")));
    }

    @Override
    public Page<HostRoomResponseDto> getAllRoom(Long accommodationId, Pageable pageable) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation Not Found"));

        return roomRepository.findByAccommodation(accommodation, pageable)
                .map(HostRoomResponseDto::new);
    }

    @Override
    public HostRoomResponseDto updateRoom(CustomUserDetails customUserDetails, Long roomId, RoomRequestDto roomRequestDto) {
        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room Not Found"));

        if(!room.getAccommodation().getUser().equals(user)) {
            throw new AccessDeniedException("You do not have permission to update this accommodation");
        }

        room.update(roomRequestDto.getName(), roomRequestDto.getDescription(), roomRequestDto.getCapacity(), roomRequestDto.getPricePerOverCapacity(), roomRequestDto.getPricePerNight());

        return new HostRoomResponseDto(room);
    }

    @Override
    public String deleteRoom(CustomUserDetails customUserDetails, Long roomId) {
        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room Not Found"));

        if(!room.getAccommodation().getUser().equals(user)) {
            throw new AccessDeniedException("You do not have permission to update this accommodation");
        }
        roomRepository.delete(room);
        return "Room Deleted Successfully";
    }
}
