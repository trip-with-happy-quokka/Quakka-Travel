package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.GuestAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuestAccommodationServiceImpl implements GuestAccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Override
    public Page<GuestAccommodationResponseDto> getAllAccommodation(Pageable pageable) {
        return accommodationRepository.findAll(pageable).map(GuestAccommodationResponseDto::new);
    }

    @Override
    public GuestAccommodationResponseDto getAccommodation(Long accommodationId) {
        return new GuestAccommodationResponseDto(accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation Not Found")));
    }
}
