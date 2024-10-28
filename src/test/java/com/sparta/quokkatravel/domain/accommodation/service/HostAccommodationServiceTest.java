package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.HostAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepositorySupport;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.common.exception.UnAuthorizedException;
import com.sparta.quokkatravel.domain.user.entity.User;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
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

    @Mock
    private AccommodationRepositorySupport accommodationRepositorySupport;

    private CustomUserDetails userDetails;
    private User user;
    private Accommodation accommodation;
    private MultipartFile image;

    @BeforeEach
    void setUp() {
        // 공통으로 사용할 user 및 userDetails 설정
        userDetails = mock(CustomUserDetails.class);
        when(userDetails.getEmail()).thenReturn("user@example.com");

        user = new User();
        setId(user, "id", 1L);

        // 공통으로 사용할 accommodation 설정
        accommodation = new Accommodation("숙소1", "설명1", "주소1", user);
        setId(accommodation, "id", 1L);

        // UserRepository가 호출되면 user 반환하도록 설정
        when(userRepository.findByEmailOrElseThrow(anyString())).thenReturn(user);
    }

    // === createAccommodation 테스트 ===

    @Test
    void createAccommodation_Success() throws IOException {
        // given
        AccommodationRequestDto requestDto = new AccommodationRequestDto("숙소1", "설명1", "주소1");
        when(accommodationRepository.save(any(Accommodation.class))).thenReturn(accommodation);

        // when
        HostAccommodationResponseDto responseDto = hostAccommodationService.createAccommodation(userDetails, image, requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("숙소1", responseDto.getName());
        verify(accommodationRepository, times(1)).save(any(Accommodation.class));
    }

    @Test
    void createAccommodation_Failure_UserNotFound() {
        // given
        AccommodationRequestDto requestDto = new AccommodationRequestDto("숙소1", "설명1", "주소1");
        when(userRepository.findByEmailOrElseThrow(anyString())).thenThrow(new NotFoundException("User Not Found"));

        // when & then
        assertThrows(NotFoundException.class, () -> {
            hostAccommodationService.createAccommodation(userDetails, image, requestDto);
        });
        verify(accommodationRepository, never()).save(any(Accommodation.class));
    }

    // === getAllAccommodation 테스트 ===

    @Test
    void getAllAccommodation_Success() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Accommodation> page = new PageImpl<>(Collections.singletonList(accommodation));
        when(accommodationRepository.findByUser(any(User.class), eq(pageable))).thenReturn(page);

        // when
        Page<HostAccommodationResponseDto> result = hostAccommodationService.getAllAccommodation(userDetails, pageable);

        // then
        assertEquals(1, result.getTotalElements());
        verify(accommodationRepository, times(1)).findByUser(any(User.class), eq(pageable));
    }

    @Test
    void getAllAccommodation_Failure_UserNotFound() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository.findByEmailOrElseThrow(anyString())).thenThrow(new NotFoundException("User Not Found"));

        // when & then
        assertThrows(NotFoundException.class, () -> {
            hostAccommodationService.getAllAccommodation(userDetails, pageable);
        });
        verify(accommodationRepository, never()).findByUser(any(User.class), eq(pageable));
    }

    // === getAccommodation 테스트 ===

    @Test
    void getAccommodation_Success() {
        // given
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));

        // when
        HostAccommodationResponseDto result = hostAccommodationService.getAccommodation(userDetails, 1L);

        // then
        assertNotNull(result);
        assertEquals("숙소1", result.getName());
        verify(accommodationRepository, times(1)).findById(1L);
    }

    @Test
    void getAccommodation_Failure_NotFound() {
        // given
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> {
            hostAccommodationService.getAccommodation(userDetails, 1L);
        });
    }

    @Test
    void getAccommodation_Failure_AccessDenied() {
        // given
        User anotherUser = new User();
        setId(anotherUser, "id", 2L);
        Accommodation accommodationOfAnotherUser = new Accommodation("숙소2", "설명2", "주소2", anotherUser);
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodationOfAnotherUser));

        // when & then
        assertThrows(UnAuthorizedException.class, () -> {
            hostAccommodationService.getAccommodation(userDetails, 1L);
        });
    }

    // === updateAccommodation 테스트 ===

    @Test
    void updateAccommodation_Success() {
        // given
        AccommodationRequestDto requestDto = new AccommodationRequestDto("변경된 숙소", "변경된 설명", "변경된 주소");
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));

        // when
        HostAccommodationResponseDto result = hostAccommodationService.updateAccommodation(userDetails, 1L, requestDto);

        // then
        assertNotNull(result);
        assertEquals("변경된 숙소", result.getName());
        verify(accommodationRepository, times(1)).save(accommodation);
    }

    @Test
    void updateAccommodation_Failure_AccessDenied() {
        // given
        User anotherUser = new User();
        setId(anotherUser, "id", 2L);
        Accommodation accommodationOfAnotherUser = new Accommodation("숙소2", "설명2", "주소2", anotherUser);
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodationOfAnotherUser));

        // when & then
        assertThrows(AccessDeniedException.class, () -> {
            hostAccommodationService.updateAccommodation(userDetails, 1L, new AccommodationRequestDto("변경된 이름", "변경된 설명", "변경된 주소"));
        });
    }

    // === deleteAccommodation 테스트 ===

    @Test
    void deleteAccommodation_Success() {
        // given
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodation));

        // when
        String result = hostAccommodationService.deleteAccommodation(userDetails, 1L);

        // then
        assertEquals("Accommodation Deleted", result);
        verify(accommodationRepository, times(1)).delete(accommodation);
    }

    @Test
    void deleteAccommodation_Failure_AccessDenied() {
        // given
        User anotherUser = new User();
        setId(anotherUser, "id", 2L);
        Accommodation accommodationOfAnotherUser = new Accommodation("숙소2", "설명2", "주소2", anotherUser);
        when(accommodationRepository.findById(anyLong())).thenReturn(Optional.of(accommodationOfAnotherUser));

        // when & then
        assertThrows(AccessDeniedException.class, () -> {
            hostAccommodationService.deleteAccommodation(userDetails, 1L);
        });
        verify(accommodationRepository, never()).delete(any(Accommodation.class));
    }









    private void setId(Object target, String fieldName, Long value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
