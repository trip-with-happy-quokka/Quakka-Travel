package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.HostAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.common.exception.UnAuthorizedException;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.s3.S3Uploader;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.repository.AccommodationSearchRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HostAccommodationServiceTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private S3Uploader s3Uploader;

    @Mock
    private AccommodationSearchRepository accommodationSearchRepository;

    @InjectMocks
    private HostAccommodationServiceImpl hostAccommodationService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    public void 숙소_생성_테스트() throws IOException {
        // given
        CustomUserDetails userDetails = new CustomUserDetails(new User("host@example.com", "", "", UserRole.HOST));
        AccommodationRequestDto requestDto = new AccommodationRequestDto("Test Accommodation", "Description", "Seoul");
        MultipartFile image = Mockito.mock(MultipartFile.class);

        User user = new User("host@example.com", "", "", UserRole.HOST);
        String uploadedImageUrl = "https://example.com/image.jpg";
        Accommodation accommodation = new Accommodation("Test Accommodation", "Description", "Seoul", uploadedImageUrl, user);

        given(userRepository.findByEmailOrElseThrow(userDetails.getEmail())).willReturn(user);
        given(s3Uploader.upload(image, "accommodation")).willReturn(uploadedImageUrl);
        given(accommodationRepository.save(any(Accommodation.class))).willReturn(accommodation);

        // when
        HostAccommodationResponseDto responseDto = hostAccommodationService.createAccommodation(userDetails, image, requestDto);

        // then
        assertEquals("Test Accommodation", responseDto.getName());
        verify(accommodationRepository, times(1)).save(any(Accommodation.class));
        verify(accommodationSearchRepository, times(1)).save(any(AccommodationDocument.class));
    }

    @Test
    public void 모든_숙소_조회_테스트() {
        // given
        CustomUserDetails userDetails = new CustomUserDetails(new User("host@example.com", "", "", UserRole.HOST));
        Pageable pageable = PageRequest.of(0, 10);
        User user = new User("host@example.com", "", "", UserRole.HOST);
        Accommodation accommodation = new Accommodation("Test Accommodation", "Description", "Seoul", "https://example.com/image.jpg", user);
        Page<Accommodation> accommodationPage = new PageImpl<>(List.of(accommodation));

        given(userRepository.findByEmailOrElseThrow(userDetails.getEmail())).willReturn(user);
        given(accommodationRepository.findByUser(user, pageable)).willReturn(accommodationPage);

        // when
        Page<HostAccommodationResponseDto> result = hostAccommodationService.getAllAccommodation(userDetails, pageable);

        // then
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Accommodation", result.getContent().get(0).getName());
        verify(accommodationRepository, times(1)).findByUser(user, pageable);
    }

    @Test
    public void 특정_숙소_조회_권한_테스트() {
        // given
        CustomUserDetails userDetails = new CustomUserDetails(new User(1L, "host@example.com", "", "", UserRole.HOST));
        Long accommodationId = 1L;
        User user = new User(1L, "host@example.com", "", "", UserRole.HOST);
        Accommodation accommodation = new Accommodation("Test Accommodation", "Description", "Seoul", "https://example.com/image.jpg", user);

        given(userRepository.findByEmailOrElseThrow(userDetails.getEmail())).willReturn(user);
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));

        // when
        HostAccommodationResponseDto result = hostAccommodationService.getAccommodation(userDetails, accommodationId);

        // then
        assertEquals("Test Accommodation", result.getName());
        verify(accommodationRepository, times(1)).findById(accommodationId);
    }

    @Test
    public void 특정_숙소_조회_NotFoundException_테스트() {
        // given
        CustomUserDetails userDetails = new CustomUserDetails(new User(1L, "host@example.com", "", "", UserRole.HOST));
        Long accommodationId = 1L;

        given(userRepository.findByEmailOrElseThrow(userDetails.getEmail())).willReturn(new User(1L, "host@example.com", "", "", UserRole.HOST));
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () ->
                hostAccommodationService.getAccommodation(userDetails, accommodationId));
    }

    @Test
    public void 숙소_수정_권한_없음_예외_테스트() {
        // given
        CustomUserDetails userDetails = new CustomUserDetails(new User(1L, "host@example.com", "", "", UserRole.HOST));
        Long accommodationId = 1L;
        User anotherUser = new User(2L, "another@example.com", "", "", UserRole.HOST); // 다른 사용자
        Accommodation accommodation = new Accommodation("Test Accommodation", "Description", "Seoul", "https://example.com/image.jpg", anotherUser);
        AccommodationRequestDto requestDto = new AccommodationRequestDto("Updated Accommodation", "Updated Description", "Busan");

        given(userRepository.findByEmailOrElseThrow(userDetails.getEmail())).willReturn(new User("host@example.com", "", "", UserRole.HOST));
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));

        // when & then
        assertThrows(AccessDeniedException.class, () ->
                hostAccommodationService.updateAccommodation(userDetails, accommodationId, requestDto));
    }

    @Test
    public void 숙소_삭제_테스트() {
        // given
        CustomUserDetails userDetails = new CustomUserDetails(new User("host@example.com", "", "", UserRole.HOST));
        Long accommodationId = 1L;
        User user = new User("host@example.com", "", "", UserRole.HOST);
        Accommodation accommodation = new Accommodation("Test Accommodation", "Description", "Seoul", "https://example.com/image.jpg", user);
        AccommodationDocument accommodationDocument = new AccommodationDocument(accommodation);

        given(userRepository.findByEmailOrElseThrow(userDetails.getEmail())).willReturn(user);
        given(accommodationRepository.findById(accommodationId)).willReturn(Optional.of(accommodation));
        given(accommodationSearchRepository.findByAccommodationIdOrElseThrow(accommodationId)).willReturn(accommodationDocument);

        // when
        String result = hostAccommodationService.deleteAccommodation(userDetails, accommodationId);

        // then
        assertEquals("Accommodation Deleted", result);
        verify(accommodationRepository, times(1)).delete(accommodation);
        verify(accommodationSearchRepository, times(1)).delete(any(AccommodationDocument.class));
    }
}
