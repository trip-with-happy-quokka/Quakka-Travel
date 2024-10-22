package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepositorySupport;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationSerivceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationRepositorySupport accommodationRepositorySupport;

    @Override
    public AccommodationResponseDto createAccommodation(CustomUserDetails customUserDetails, AccommodationRequestDto accommodationRequestDto) {

        // CustomUserDetails 에서 User Role 을 가져와 인증을 해야함

        Accommodation accommodation = new Accommodation(accommodationRequestDto);

        return null;
    }

    @Override
    public AccommodationResponseDto getAllAccommodation(CustomUserDetails customUserDetails) {
        return null;
    }

    @Override
    public AccommodationResponseDto getAccommodation(CustomUserDetails customUserDetails, Long accommodationId) {
        return null;
    }

    @Override
    public AccommodationResponseDto updateAccommodation(CustomUserDetails customUserDetails, Long accommodationId, AccommodationRequestDto accommodationRequestDto) {
        return null;
    }

    @Override
    public AccommodationResponseDto deleteAccommodation(CustomUserDetails customUserDetails, Long accommodationId) {
        return null;
    }
}
