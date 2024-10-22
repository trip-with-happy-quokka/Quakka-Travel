package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationResponseDto;

public interface AccommodationService {

    AccommodationResponseDto createAccommodation(AccommodationRequestDto accommodationRequestDto);
    AccommodationResponseDto getAccommodation();
    AccommodationResponseDto updateAccommodation(AccommodationRequestDto accommodationRequestDto);
    AccommodationResponseDto deleteAccommodation();

}
