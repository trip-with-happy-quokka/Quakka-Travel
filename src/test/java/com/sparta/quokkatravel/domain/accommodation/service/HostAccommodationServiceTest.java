package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.HostAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HostAccommodationServiceTest {
    @InjectMocks
    private HostAccommodationServiceImpl hostAccommodationService;

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void createAccommodation_Success() {
        // given
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        AccommodationRequestDto requestDto = new AccommodationRequestDto("숙소1", "설명1", "주소1");
        User user = new User();
        when(userRepository.findByEmailOrElseThrow(anyString())).thenReturn(user);

        Accommodation accommodation = new Accommodation("숙소1", "설명1", "주소1", user);
        when(accommodationRepository.save(any(Accommodation.class))).thenReturn(accommodation);

        // when
        HostAccommodationResponseDto responseDto = hostAccommodationService.createAccommodation(userDetails, requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("숙소1", responseDto.getName());
        verify(accommodationRepository, times(1)).save(any(Accommodation.class));
    }

    @Test
    void getAllAccommodation_Success() {
        // given
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        User user = new User();
        when(userRepository.findByEmailOrElseThrow(anyString())).thenReturn(user);

        PageRequest pageable = PageRequest.of(0, 10);
        Accommodation accommodation = new Accommodation("숙소1", "설명1", "주소1", user);
        Page<Accommodation> page = new PageImpl<>(Collections.singletonList(accommodation));
        when(accommodationRepository.findByUser(any(User.class), eq(pageable))).thenReturn(page);

        // when
        Page<HostAccommodationResponseDto> result = hostAccommodationService.getAllAccommodation(userDetails, pageable);

        // then
        assertEquals(1, result.getTotalElements());
        verify(accommodationRepository, times(1)).findByUser(any(User.class), eq(pageable));
    }

    @Test
    void getAccommodation_NotFound() {
        // given
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> {
            hostAccommodationService.getAccommodation(mock(CustomUserDetails.class), 1L);
        });
    }

    @Test
    void updateAccommodation_AccessDenied() {
        // given
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        User user1 = new User(); // 현재 사용자
        User user2 = new User(); // 다른 사용자
        Accommodation accommodation = new Accommodation("숙소1", "설명1", "주소1", user2);

        when(userRepository.findByEmailOrElseThrow(anyString())).thenReturn(user1);
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));

        // when & then
        assertThrows(AccessDeniedException.class, () -> {
            hostAccommodationService.updateAccommodation(userDetails, 1L, new AccommodationRequestDto("변경된 이름", "변경된 설명", "변경된 주소"));
        });
    }

    @Test
    void deleteAccommodation_AccessDenied() {
        // given
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        User user1 = new User(); // 현재 사용자
        User user2 = new User(); // 다른 사용자
        Accommodation accommodation = new Accommodation("숙소1", "설명1", "주소1", user2);

        when(userRepository.findByEmailOrElseThrow(anyString())).thenReturn(user1);
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));

        // when & then
        assertThrows(AccessDeniedException.class, () -> {
            hostAccommodationService.deleteAccommodation(userDetails, 1L);
        });
    }
}
