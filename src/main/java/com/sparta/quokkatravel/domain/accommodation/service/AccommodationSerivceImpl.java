package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationSerivceImpl implements AccommodationService {
    @Override
    public AccommodationResponseDto createAccommodation(AccommodationRequestDto accommodationRequestDto) {
        return new AccommodationResponseDto();
    }

    @Override
    public AccommodationResponseDto getAccommodation() {
        return new AccommodationResponseDto();
    }

    @Override
    public AccommodationResponseDto updateAccommodation(AccommodationRequestDto accommodationRequestDto) {
        return new AccommodationResponseDto();
    }

    @Override
    public AccommodationResponseDto deleteAccommodation() {
        return new AccommodationResponseDto();
    }
}
