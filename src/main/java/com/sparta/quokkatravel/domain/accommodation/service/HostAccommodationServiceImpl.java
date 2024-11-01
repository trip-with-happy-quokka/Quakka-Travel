package com.sparta.quokkatravel.domain.accommodation.service;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.dto.HostAccommodationResponseDto;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.aop.InvalidateAccommodationCache;
import com.sparta.quokkatravel.domain.common.jwt.CustomUserDetails;
import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.common.exception.UnAuthorizedException;
import com.sparta.quokkatravel.domain.common.s3.S3Uploader;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.repository.AccommodationSearchRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostAccommodationServiceImpl implements HostAccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final AccommodationSearchRepository accommodationSearchRepository;

    @Override
    @Transactional
    public HostAccommodationResponseDto createAccommodation(CustomUserDetails customUserDetails, MultipartFile image, AccommodationRequestDto accommodationRequestDto) throws IOException {

        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());
        String url = "";
        // image upload
        if(image != null) {
            url = s3Uploader.upload(image, "accommodation");
        }
        Accommodation accommodation = new Accommodation(accommodationRequestDto.getName(), accommodationRequestDto.getDescription(), accommodationRequestDto.getAddress(), url, user);
        log.info("Accommodation created: {}", accommodation);
        Accommodation saved = accommodationRepository.save(accommodation);
        AccommodationDocument accommodationDocument = new AccommodationDocument(accommodation);
        log.info("Document created: {}", accommodationDocument);
        accommodationSearchRepository.save(accommodationDocument);

        return new HostAccommodationResponseDto(saved);
    }

    @Override
    public Page<HostAccommodationResponseDto> getAllAccommodation(CustomUserDetails customUserDetails, Pageable pageable) {

        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());

        return accommodationRepository.findByUser(user, pageable)
                        .map(HostAccommodationResponseDto::new);
    }

    @Override
    public HostAccommodationResponseDto getAccommodation(CustomUserDetails customUserDetails, Long accommodationId) {

        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation Not Found"));

        if (!accommodation.getUser().getId().equals(user.getId())) {
            throw new UnAuthorizedException("You do not own this accommodation");
        }

        return new HostAccommodationResponseDto(accommodation);
    }

    @Override
    @Transactional
    @InvalidateAccommodationCache
    public HostAccommodationResponseDto updateAccommodation(CustomUserDetails customUserDetails, Long accommodationId, AccommodationRequestDto accommodationRequestDto) {

        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();

        if(!accommodation.getUser().equals(user)) {
            throw new AccessDeniedException("You do not have permission to update this accommodation");
        }

        accommodation.update(accommodationRequestDto.getName(), accommodationRequestDto.getDescription(), accommodationRequestDto.getAddress());

        return new HostAccommodationResponseDto(accommodation);
    }

    @Override
    @Transactional
    @InvalidateAccommodationCache
    public String deleteAccommodation(CustomUserDetails customUserDetails, Long accommodationId) {

        User user = userRepository.findByEmailOrElseThrow(customUserDetails.getEmail());
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();

        if(!accommodation.getUser().equals(user)) {
            throw new AccessDeniedException("You do not have permission to update this accommodation");
        }

        accommodationRepository.delete(accommodation);

        return "Accommodation Deleted";
    }
}
