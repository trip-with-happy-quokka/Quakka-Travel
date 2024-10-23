package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.HostAccommodationResponseDto;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HostAccommodationService {

    // 생성
    HostAccommodationResponseDto createAccommodation(CustomUserDetails customUserDetails, AccommodationRequestDto accommodationRequestDto);

    // 조회
    Page<HostAccommodationResponseDto> getAllAccommodation(CustomUserDetails customUserDetails, Pageable pageable);
    HostAccommodationResponseDto getAccommodation(CustomUserDetails customUserDetails, Long accommodationId);

    // 수정
    HostAccommodationResponseDto updateAccommodation(CustomUserDetails customUserDetails, Long accommodationId, AccommodationRequestDto accommodationRequestDto);

    // 삭제
    String deleteAccommodation(CustomUserDetails customUserDetails, Long accommodationId);
}
