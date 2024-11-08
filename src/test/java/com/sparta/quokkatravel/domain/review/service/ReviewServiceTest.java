package com.sparta.quokkatravel.domain.review.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.review.dto.ReviewRequestDto;
import com.sparta.quokkatravel.domain.review.dto.ReviewResponseDto;
import com.sparta.quokkatravel.domain.review.entity.Review;
import com.sparta.quokkatravel.domain.review.repository.ReviewRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void 리뷰_생성_테스트() {
        // given
        String email = "test@example.com";
        ReviewRequestDto requestDto = new ReviewRequestDto(1L, 5L, "Great place!");
        Accommodation accommodation = new Accommodation();
        User user = new User(email, "password", "nickname", UserRole.USER);

        // Mock repository responses
        given(accommodationRepository.findById(requestDto.getAccommodationId())).willReturn(Optional.of(accommodation));
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // Stub save method to return the same review instance
        given(reviewRepository.save(any(Review.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        ReviewResponseDto responseDto = reviewService.createReview(email, requestDto);

        // then
        assertEquals(requestDto.getRating(), responseDto.getRating());
        assertEquals(requestDto.getContent(), responseDto.getContent());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void 특정_숙소의_리뷰_조회_테스트() {
        // given
        String email = "test@example.com";
        Long accommodationId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        User user = new User(email, "password", "nickname", UserRole.USER);
        Review review = new Review(new Accommodation(), user, 5L, "Nice stay");
        Page<Review> reviews = new PageImpl<>(List.of(review));

        given(accommodationRepository.existsById(accommodationId)).willReturn(true);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(reviewRepository.findByAccommodation_Id(accommodationId, pageable)).willReturn(reviews);

        // when
        Page<ReviewResponseDto> response = reviewService.getReviewsByAccommodation(email, accommodationId, pageable);

        // then
        assertEquals(1, response.getTotalElements());
        assertEquals(review.getContent(), response.getContent().get(0).getContent());
        verify(reviewRepository, times(1)).findByAccommodation_Id(accommodationId, pageable);
    }

    @Test
    void 리뷰_수정_테스트() {
        // given
        Long reviewId = 1L;
        String email = "test@example.com";
        ReviewRequestDto requestDto = new ReviewRequestDto(1L, 4L, "Updated review");
        User user = new User(email, "password", "nickname", UserRole.USER);
        Review review = new Review(new Accommodation(), user, 5L, "Old review");

        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // when
        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, email, requestDto);

        // then
        assertEquals(requestDto.getRating(), responseDto.getRating());
        assertEquals(requestDto.getContent(), responseDto.getContent());
        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    void 리뷰_삭제_테스트() {
        // given
        Long reviewId = 1L;
        String email = "test@example.com";
        User user = new User(email, "password", "nickname", UserRole.USER);
        Review review = new Review(new Accommodation(), user, 5L, "Sample review");

        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // when
        reviewService.deleteReview(reviewId, email);

        // then
        verify(reviewRepository, times(1)).delete(review);
    }
}
