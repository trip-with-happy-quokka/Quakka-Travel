package com.sparta.quokkatravel.domain.room.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
import com.sparta.quokkatravel.domain.room.dto.GuestRoomResponseDto;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.room.repository.RoomRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GuestRoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private GuestRoomServiceImpl guestRoomService;

    private User user;
    private Accommodation accommodation;
    private Room room;
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(user, "email", "test@example.com");

        accommodation = new Accommodation();
        ReflectionTestUtils.setField(accommodation, "id", 1L);
        ReflectionTestUtils.setField(accommodation, "name", "Test Hotel");
        ReflectionTestUtils.setField(accommodation, "description", "Hotel Description");
        ReflectionTestUtils.setField(accommodation, "address", "Hotel Address");
        ReflectionTestUtils.setField(accommodation, "user", user);

        room = new Room();
        ReflectionTestUtils.setField(room, "id", 1L);
        ReflectionTestUtils.setField(room, "name", "Test Room");
        ReflectionTestUtils.setField(room, "description", "Test Room Description");
        ReflectionTestUtils.setField(room, "capacity", 2L);
        ReflectionTestUtils.setField(room, "pricePerOverCapacity", 10000L);
        ReflectionTestUtils.setField(room, "pricePerNight", 20000L);
        ReflectionTestUtils.setField(room, "accommodation", accommodation);

        coupon = new Coupon();
        ReflectionTestUtils.setField(coupon, "id", 1L);
        ReflectionTestUtils.setField(coupon, "name", "Test Coupon");
        ReflectionTestUtils.setField(coupon, "code", "Test Code");

    }

    @Test
    void 객실_조회_성공() {
        // given
        Long userId = 1L;
        Long accommodationId = accommodation.getId();
        Long roomId = room.getId();

        given(roomRepository.findById(roomId)).willReturn(Optional.of(room));

        // when
        GuestRoomResponseDto response = guestRoomService.getRoom(userId, accommodationId, roomId);

        // then
        assertNotNull(response);
        assertEquals(roomId, response.getId());
    }

    @Test
    void 객실_조회_실패_존재하지않는_객실() {
        // given
        Long userId = 1L;
        Long accommodationId = 1L;
        Long roomId = 3L;

        given(roomRepository.findById(roomId)).willReturn(Optional.empty());

        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                guestRoomService.getRoom(userId, accommodationId, roomId)
        );

        // then
        assertEquals("Room Not Found", exception.getMessage());
    }

    @Test
    void 객실_조회_실패_숙소에_해당객실_없음() {
        // given
        Long userId = 1L;
        Long accommodationId = 2L; // 조회하려는 숙소 ID
        Long roomId = 1L; // 존재하는 객실 ID

        // 다른 숙소 ID 설정
        Accommodation otherAccommodation = new Accommodation();
        ReflectionTestUtils.setField(otherAccommodation, "id", 99L); // 의도적으로 다른 ID 설정

        // room 객체의 accommodation을 다른 숙소로 설정
        ReflectionTestUtils.setField(room, "accommodation", otherAccommodation);

        given(roomRepository.findById(roomId)).willReturn(Optional.of(room));

        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                guestRoomService.getRoom(userId, accommodationId, roomId)
        );

        // then
        assertEquals("해당 숙소에는 그런 객실이 없습니다.", exception.getMessage());
    }

    @Test
    void 모든_객실_조회() {
        // given
        Long accommodationId = accommodation.getId();
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Room> roomPage = new PageImpl<>(Collections.singletonList(room), pageable, 1);

        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));
        given(roomRepository.findByAccommodation(accommodation, pageable)).willReturn(roomPage);

        // when
        Page<GuestRoomResponseDto> response = guestRoomService.getAllRoom(accommodationId, pageable);

        // then
        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(room.getId(), response.getContent().get(0).getId());
    }

    @Test
    void 모든_객실_조회_실패_존재하지않는_숙소() {
        // given
        Long accommodationId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.empty());

        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                guestRoomService.getAllRoom(accommodationId, pageable)
        );

        // then
        assertEquals("Accommodation Not Found", exception.getMessage());
    }
}