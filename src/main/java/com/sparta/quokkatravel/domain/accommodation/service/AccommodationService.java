package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationResponseDto;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;

public interface AccommodationService {

    // 생성
    AccommodationResponseDto createAccommodation(CustomUserDetails customUserDetails, AccommodationRequestDto accommodationRequestDto);
    // 조회
    AccommodationResponseDto getAllAccommodation(CustomUserDetails customUserDetails);
    AccommodationResponseDto getAccommodation(CustomUserDetails customUserDetails, Long accommodationId);
    // 수정
    AccommodationResponseDto updateAccommodation(CustomUserDetails customUserDetails, Long accommodationId, AccommodationRequestDto accommodationRequestDto);
    // 삭제
    AccommodationResponseDto deleteAccommodation(CustomUserDetails customUserDetails, Long accommodationId);

}
