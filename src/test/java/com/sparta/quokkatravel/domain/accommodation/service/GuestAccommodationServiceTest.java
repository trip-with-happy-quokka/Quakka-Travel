package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.GuestAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GuestAccommodationServiceTest {

    @InjectMocks
    private GuestAccommodationServiceImpl guestAccommodationService;

    @Mock
    private AccommodationRepository accommodationRepository;

    @Test
    void getAllAccommodation_Success() {
        // given
        Pageable pageable = PageRequest.of(0, 10); // 페이지 요청
        Accommodation accommodation = new Accommodation(roomRequestDto.getName(), roomRequestDto.getDescription(), roomRequestDto.getCapacity(), roomRequestDto.getPricePerOverCapacity(), roomRequestDto.getPricePerNight()); // 테스트용 숙소 객체 생성
        Page<Accommodation> accommodationPage = new PageImpl<>(Collections.singletonList(accommodation)); // 한 개의 숙소가 있는 페이지 객체 생성

        when(accommodationRepository.findAll(pageable)).thenReturn(accommodationPage); // accommodationRepository의 동작 정의

        // when
        Page<GuestAccommodationResponseDto> result = guestAccommodationService.getAllAccommodation(pageable);

        // then
        assertNotNull(result); // 결과가 null이 아님을 확인
        assertEquals(1, result.getTotalElements()); // 페이지 내 요소의 총 개수가 1개임을 확인
        verify(accommodationRepository, times(1)).findAll(pageable); // accommodationRepository의 findAll 호출이 한 번 발생했는지 확인
    }

    @Test
    void getAccommodation_Success() {
        // given
        Long accommodationId = 1L;
        Accommodation accommodation = new Accommodation(roomRequestDto.getName(), roomRequestDto.getDescription(), roomRequestDto.getCapacity(), roomRequestDto.getPricePerOverCapacity(), roomRequestDto.getPricePerNight()); // 테스트용 숙소 객체 생성
        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation)); // accommodationRepository의 findById 동작 정의

        // when
        GuestAccommodationResponseDto result = guestAccommodationService.getAccommodation(accommodationId);

        // then
        assertNotNull(result); // 결과가 null이 아님을 확인
        verify(accommodationRepository, times(1)).findById(accommodationId); // accommodationRepository의 findById 호출이 한 번 발생했는지 확인
    }

    @Test
    void getAccommodation_NotFound() {
        // given
        Long accommodationId = 1L;
        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.empty()); // 숙소가 없는 경우를 가정

        // when & then
        assertThrows(NotFoundException.class, () -> guestAccommodationService.getAccommodation(accommodationId)); // NotFoundException이 발생하는지 확인
        verify(accommodationRepository, times(1)).findById(accommodationId); // accommodationRepository의 findById 호출이 한 번 발생했는지 확인
    }
}
