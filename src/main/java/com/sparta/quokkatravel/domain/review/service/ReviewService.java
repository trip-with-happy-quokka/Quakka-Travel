package com.sparta.quokkatravel.domain.review.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.review.dto.ReviewRequestDto;
import com.sparta.quokkatravel.domain.review.dto.ReviewResponseDto;
import com.sparta.quokkatravel.domain.review.entity.Review;
import com.sparta.quokkatravel.domain.review.repository.ReviewRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;

    // 리뷰 생성
    @Transactional
    public ReviewResponseDto createReview(CustomUserDetails customUserDetails, ReviewRequestDto requestDto) {
        Accommodation accommodation = accommodationRepository.findById(requestDto.getAccommodationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 숙소가 없습니다."));
        User user = userRepository.findByEmail(customUserDetails.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        Review review = new Review(accommodation, user, requestDto.getRating(), requestDto.getContent());
        reviewRepository.save(review);

        return new ReviewResponseDto(review.getId(), accommodation.getId(), review.getRating(), review.getContent());
    }

    // 특정 숙소의 리뷰 전체 목록 조회
    @Transactional
    public Page<ReviewResponseDto> getReviewsByAccommodation(CustomUserDetails customUserDetails, Long accommodationId, Pageable pageable) {

        // 숙소가 존재하는지 확인
        if (!accommodationRepository.existsById(accommodationId)) {
            throw new IllegalArgumentException("해당 숙소가 존재하지 않습니다.");
        }
        User user = userRepository.findByEmail(customUserDetails.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        Page<Review> reviews = reviewRepository.findByAccommodation_Id(accommodationId, pageable);
        return reviews.map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getAccommodation().getId(),
                        review.getRating(),
                        review.getContent()
        ));
    }

    // 리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, CustomUserDetails customUserDetails, ReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));

        if (!review.getUser().getEmail().equals(customUserDetails.getEmail())) {
            throw new IllegalArgumentException("리뷰 수정 권한이 없습니다.");
        }

        review.update(requestDto.getRating(), requestDto.getContent());
        return new ReviewResponseDto(review.getId(), review.getAccommodation().getId(), review.getRating(), review.getContent());
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId, CustomUserDetails customUserDetails) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));

        if (!review.getUser().getEmail().equals(customUserDetails.getEmail())) {
            throw new IllegalArgumentException("리뷰 삭제 권한이 없습니다.");
        }

        reviewRepository.delete(review);
    }

}
