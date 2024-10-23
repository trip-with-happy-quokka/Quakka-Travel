package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationOfGuestResponseDto;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationOfGuestServiceImpl implements AccommodationOfGuestService {

    private final AccommodationRepository accommodationRepository;

    @Override
    public Page<AccommodationOfGuestResponseDto> getAllAccommodation(Pageable pageable) {
        return accommodationRepository.findAll(pageable).map(AccommodationOfGuestResponseDto::new);
    }

    @Override
    public AccommodationOfGuestResponseDto getAccommodation(Long accommodationId) {
        return new AccommodationOfGuestResponseDto(accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation Not Found")));
    }
}
