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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostRoomServiceImpl implements HostRoomService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final AccommodationRepository accommodationRepository;

    @Override
    @Transactional
    public HostRoomResponseDto createRoom(CustomUserDetails userDetails, Long accommodationId, RoomRequestDto roomRequestDto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(() -> new NotFoundException("accommodation not found"));
        Room room = new Room(roomRequestDto, accommodation);
        roomRepository.save(room);
        return new HostRoomResponseDto(room);
    }

    @Override
    public HostRoomResponseDto getRoom(CustomUserDetails customUserDetails, Long accommodationId, Long roomId) {
        // 유저 조회
        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());

        // 유저가 해당 숙소를 소유하고 있는지 확인
        Accommodation accommodation = user.getAccommodations().stream()
                .filter(acc -> acc.getId().equals(accommodationId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("You do not own this accommodation"));

        // 숙소에 속한 객실(Room) 조회
        Room room = accommodation.getRooms().stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Room not found in your accommodation"));

        return new HostRoomResponseDto(room);
    }

    @Override
    public Page<HostRoomResponseDto> getAllRoom(CustomUserDetails customUserDetails, Long accommodationId, Pageable pageable) {

        // 유저 조회
        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());

        // 유저가 해당 숙소를 소유하고 있는지 확인
        Accommodation accommodation = user.getAccommodations().stream()
                .filter(acc -> acc.getId().equals(accommodationId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("You do not own this accommodation"));

        return roomRepository.findByAccommodation(accommodation, pageable)
                .map(HostRoomResponseDto::new);
    }

    @Override
    @Transactional
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
    @Transactional
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
