package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationOfHostResponseDto;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccommodationOfHostService {

    // 생성
    AccommodationOfHostResponseDto createAccommodation(CustomUserDetails customUserDetails, AccommodationRequestDto accommodationRequestDto);

    // 조회
    Page<AccommodationOfHostResponseDto> getAllAccommodation(CustomUserDetails customUserDetails, Pageable pageable);
    AccommodationOfHostResponseDto getAccommodation(CustomUserDetails customUserDetails, Long accommodationId);

    // 수정
    AccommodationOfHostResponseDto updateAccommodation(CustomUserDetails customUserDetails, Long accommodationId, AccommodationRequestDto accommodationRequestDto);

    // 삭제
    AccommodationOfHostResponseDto deleteAccommodation(CustomUserDetails customUserDetails, Long accommodationId);
}
