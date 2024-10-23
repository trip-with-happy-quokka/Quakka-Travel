package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationOfGuestResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccommodationOfGuestService {

    // 조회
    Page<AccommodationOfGuestResponseDto> getAllAccommodation(Pageable pageable);
    AccommodationOfGuestResponseDto getAccommodation(Long accommodationId);
}
