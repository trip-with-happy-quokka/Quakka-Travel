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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GuestAccommodationServiceTest {

    @Mock
    private AccommodationRepository accommodationRepository; // AccommodationRepository를 Mock으로 생성

    @InjectMocks
    private GuestAccommodationServiceImpl guestAccommodationService; // Mock이 주입된 서비스 객체 생성

    @Test
    public void 모든_숙소_조회_테스트() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Accommodation accommodation = new Accommodation("Test Accommodation", "Seoul", "Description", "100.0", null);
        Page<Accommodation> accommodationPage = new PageImpl<>(List.of(accommodation));

        // accommodationRepository.findAll() 호출 시 accommodationPage를 리턴하도록 설정
        given(accommodationRepository.findAll(pageable)).willReturn(accommodationPage);

        // when
        Page<GuestAccommodationResponseDto> result = guestAccommodationService.getAllAccommodation(pageable);

        // then
        // 조회 결과가 기대한 Accommodation 이름을 포함하는지 검증
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Accommodation", result.getContent().get(0).getName());
        verify(accommodationRepository, times(1)).findAll(pageable); // findAll() 호출 횟수 검증
    }

    @Test
    public void 특정_숙소_조회_테스트() {
        // given
        Long userId = 1L;
        Long accommodationId = 1L;
        Accommodation accommodation = new Accommodation("Test Accommodation", "Seoul", "Description", "", null);

        // accommodationRepository.findById() 호출 시 accommodation 객체를 Optional로 리턴하도록 설정
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));

        // when
        GuestAccommodationResponseDto result = guestAccommodationService.getAccommodation(userId, accommodationId);

        // then
        // 조회된 Accommodation의 이름이 예상대로 "Test Accommodation"인지 검증
        assertEquals("Test Accommodation", result.getName());
        verify(accommodationRepository, times(1)).findById(accommodationId); // findById() 호출 횟수 검증
    }

    @Test
    public void 특정_숙소_조회_실패_테스트() {
        // given
        Long userId = 1L;
        Long accommodationId = 2L;

        // accommodationRepository.findById() 호출 시 Optional.empty()를 리턴하도록 설정
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.empty());

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                guestAccommodationService.getAccommodation(userId, accommodationId));

        // 예외 메시지가 "Accommodation Not Found"인지 검증
        assertEquals("Accommodation Not Found", exception.getMessage());
        verify(accommodationRepository, times(1)).findById(accommodationId); // findById() 호출 횟수 검증
    }
}

