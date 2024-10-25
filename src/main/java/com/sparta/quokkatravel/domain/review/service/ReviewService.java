package com.sparta.quokkatravel.domain.review.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
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

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;

    // 리뷰 생성
    public ReviewResponseDto createReview(ReviewRequestDto requestDto){
        Accommodation accommodation = accommodationRepository.findById(requestDto.getAccomodationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 숙소가 없습니다."));
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        Review review = new Review(accommodation, user, requestDto.getRating(), requestDto.getContent());
        reviewRepository.save(review);

        return new ReviewResponseDto(review.getId(), accommodation.getId(), user.getId(), review.getRating(), review.getContent());
    }

    // 특정 숙소의 리뷰 목록 조회 (페이징 처리 추가)
    public Page<ReviewResponseDto> getReviewsByAccommodation(Long accommodationId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByAccommodationId(accommodationId, pageable);
        return reviews.map(review -> new ReviewResponseDto(
                review.getId(),
                review.getAccommodation().getId(),
                review.getUser().getId(),
                review.getRating(),
                review.getContent()
        ));
    }

    // 리뷰 수정
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));

        review.update(requestDto.getRating(), requestDto.getContent());
        return new ReviewResponseDto(review.getId(), review.getAccommodation().getId(), review.getUser().getId(), review.getRating(), review.getContent());
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));
        reviewRepository.delete(review);
    }
}
