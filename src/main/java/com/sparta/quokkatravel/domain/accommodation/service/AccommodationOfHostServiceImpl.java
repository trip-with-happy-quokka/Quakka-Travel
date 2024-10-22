package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationOfHostResponseDto;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepositorySupport;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationOfHostServiceImpl implements AccommodationOfHostService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationRepositorySupport accommodationRepositorySupport;
    private final UserRepository userRepository;

    @Override
    public AccommodationOfHostResponseDto createAccommodation(CustomUserDetails customUserDetails, AccommodationRequestDto accommodationRequestDto) {

        // CustomUserDetails 에서 User Role 을 가져와 인증을 해야함

        Accommodation accommodation = new Accommodation(accommodationRequestDto.getName(), accommodationRequestDto.getDescription(), accommodationRequestDto.getAddress());
        Accommodation saved = accommodationRepository.save(accommodation);
        return new AccommodationOfHostResponseDto(saved);
    }

    @Override
    public Page<AccommodationOfHostResponseDto> getAllAccommodation(CustomUserDetails customUserDetails, Pageable pageable) {

        // CustomUserDetails 에서 User Role 을 가져와 인증을 해야함

        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());

        return accommodationRepository.findByUser(user, pageable)
                        .map(AccommodationOfHostResponseDto::new);
    }

    @Override
    public AccommodationOfHostResponseDto getAccommodation(CustomUserDetails customUserDetails, Long accommodationId) {

        // CustomUserDetails 에서 User Role 을 가져와 인증을 해야함

        return new AccommodationOfHostResponseDto(accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation Not Found")));
    }

    @Override
    public AccommodationOfHostResponseDto updateAccommodation(CustomUserDetails customUserDetails, Long accommodationId, AccommodationRequestDto accommodationRequestDto) {
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_HOST')")
    public AccommodationOfHostResponseDto deleteAccommodation(CustomUserDetails customUserDetails, Long accommodationId) {
        return null;
    }
}
