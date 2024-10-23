package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.GuestAccommodationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuestAccommodationService {

    // 조회
    Page<GuestAccommodationResponseDto> getAllAccommodation(Pageable pageable);
    GuestAccommodationResponseDto getAccommodation(Long accommodationId);
}
