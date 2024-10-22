package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationResponseDto;
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


        return null;
    }

    @Override
    public AccommodationResponseDto getAllAccommodations(CustomUserDetails customUserDetails) {
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
