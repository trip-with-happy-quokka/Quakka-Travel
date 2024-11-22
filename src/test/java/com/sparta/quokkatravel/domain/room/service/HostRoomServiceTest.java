package com.sparta.quokkatravel.domain.room.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.room.dto.HostRoomResponseDto;
import com.sparta.quokkatravel.domain.room.dto.RoomRequestDto;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.room.repository.RoomRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HostRoomServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private HostRoomServiceImpl hostRoomService;

    @Mock
    private CustomUserDetails userDetails;

    private User user;
    private Accommodation accommodation;
    private Room room;

    @BeforeEach
    void setUp() {
        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(user, "email", "test@a.com");
        ReflectionTestUtils.setField(user, "nickname", "test name");
        ReflectionTestUtils.setField(user, "userRole", UserRole.HOST);

        accommodation = new Accommodation();
        ReflectionTestUtils.setField(accommodation, "id", 1L);
        ReflectionTestUtils.setField(accommodation, "name", "Test Hotel");
        ReflectionTestUtils.setField(accommodation, "description", "Hotel Description");
        ReflectionTestUtils.setField(accommodation, "address", "Hotel Address");
        ReflectionTestUtils.setField(accommodation, "user", user);
        ReflectionTestUtils.setField(accommodation, "rooms", new ArrayList<>());

        room = new Room();
        ReflectionTestUtils.setField(room, "id", 1L);
        ReflectionTestUtils.setField(room, "name", "Deluxe Room");
        ReflectionTestUtils.setField(room, "description", "Spacious room");
        ReflectionTestUtils.setField(room, "capacity", 2L);
        ReflectionTestUtils.setField(room, "pricePerOverCapacity", 100L);
        ReflectionTestUtils.setField(room, "pricePerNight", 200L);
        ReflectionTestUtils.setField(room, "accommodation", accommodation);
    }

    @Test
    public void createRoom_Success() {
        // given
        Long accommodationId = 1L;
        RoomRequestDto roomRequestDto = new RoomRequestDto("Deluxe Room", "Spacious room", 2L, 10000L, 20000L);

        given(userDetails.getUserId()).willReturn(user.getId());
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));

        Room newRoom = new Room();
        ReflectionTestUtils.setField(newRoom, "id", 2L);
        ReflectionTestUtils.setField(newRoom, "name", roomRequestDto.getName());
        ReflectionTestUtils.setField(newRoom, "description", roomRequestDto.getDescription());
        ReflectionTestUtils.setField(newRoom, "capacity", roomRequestDto.getCapacity());
        ReflectionTestUtils.setField(newRoom, "pricePerOverCapacity", roomRequestDto.getPricePerOverCapacity());
        ReflectionTestUtils.setField(newRoom, "pricePerNight", roomRequestDto.getPricePerNight());
        ReflectionTestUtils.setField(newRoom, "accommodation", accommodation);

        given(roomRepository.save(any(Room.class))).willReturn(newRoom);

        // when
        HostRoomResponseDto responseDto = hostRoomService.createRoom(userDetails, accommodationId, roomRequestDto);

        // then
        assertNotNull(responseDto);
        assertEquals(roomRequestDto.getName(), responseDto.getName());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    public void getRoom_Success() {
        // given
        Long accommodationId = 1L;
        Long roomId = 1L;

        given(userDetails.getUserId()).willReturn(user.getId());
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));
        accommodation.getRooms().add(room);  // Add room to accommodation's room list

        // when
        HostRoomResponseDto result = hostRoomService.getRoom(userDetails, accommodationId, roomId);

        // then
        assertNotNull(result);
        assertEquals("Deluxe Room", result.getName());
    }

    @Test
    public void getAllRoom_Success() {
        // given
        Long accommodationId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Accommodation accommodation = new Accommodation("Test Hotel", "Hotel Description", "Hotel Address", "image_url", user);

        Room room2 = new Room();
        ReflectionTestUtils.setField(room2, "id", 2L);
        ReflectionTestUtils.setField(room2, "name", "Standard Room");
        ReflectionTestUtils.setField(room2, "description", "Cozy room");
        ReflectionTestUtils.setField(room2, "capacity", 2L);
        ReflectionTestUtils.setField(room2, "pricePerOverCapacity", 80L);
        ReflectionTestUtils.setField(room2, "pricePerNight", 150L);
        ReflectionTestUtils.setField(room2, "accommodation", accommodation);

        Page<Room> roomPage = new PageImpl<>(Arrays.asList(room, room2), pageable, 2);
        given(userDetails.getUserId()).willReturn(user.getId());
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));
        given(roomRepository.findByAccommodation(accommodation, pageable)).willReturn(roomPage);

        // when
        Page<HostRoomResponseDto> result = hostRoomService.getAllRoom(userDetails, accommodationId, pageable);

        // then
        assertEquals(2, result.getTotalElements());
        verify(roomRepository, times(1)).findByAccommodation(accommodation, pageable);
    }

    @Test
    public void updateRoom_Success() {
        // given
        Long roomId = 1L;
        RoomRequestDto roomRequestDto = new RoomRequestDto("Updated Room", "Updated Description", 3L, 120L, 220L);

        given(userDetails.getUserId()).willReturn(user.getId());
        given(roomRepository.findById(roomId)).willReturn(Optional.of(room));

        // when
        HostRoomResponseDto result = hostRoomService.updateRoom(userDetails, roomId, roomRequestDto);

        // then
        assertEquals("Updated Room", result.getName());
        verify(roomRepository, times(1)).findById(roomId);  // findById 호출만 검증
        verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void deleteRoom_Success() {
        // given
        Long accommodationId = 1L;
        Long roomId = 1L;

        given(userDetails.getUserId()).willReturn(user.getId());
        given(roomRepository.findById(roomId)).willReturn(Optional.of(room));

        // when
        String result = hostRoomService.deleteRoom(userDetails, accommodationId, roomId);

        // then
        assertEquals("Room Deleted Successfully", result);
        verify(roomRepository, times(1)).delete(room);
    }
}