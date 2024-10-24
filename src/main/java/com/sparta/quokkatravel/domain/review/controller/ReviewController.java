package com.sparta.quokkatravel.domain.review.controller;

import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.review.dto.ReviewRequestDto;
import com.sparta.quokkatravel.domain.review.dto.ReviewResponseDto;
import com.sparta.quokkatravel.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "Review 관련 컨트롤러")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    @Operation(summary = "리뷰 생성", description = "새로운 리뷰를 생성하는 API")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDto requestDto) {
        ReviewResponseDto createdReview = reviewService.createReview(requestDto);
        return ResponseEntity.ok(ApiResponse.success("리뷰 생성 성공", createdReview));
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "기존 리뷰를 수정하는 API")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> updateReview(
            @PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto) {
        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, requestDto);
        return ResponseEntity.ok(ApiResponse.success("리뷰 수정 성공", responseDto));
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제하는 API")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(ApiResponse.success("리뷰 삭제 성공", null));
    }

    // 특정 숙소의 리뷰 목록 조회 (페이징)
    @GetMapping("/accommodation/{accommodationId}")
    @Operation(summary = "특정 숙소 리뷰 조회", description = "특정 숙소에 대한 리뷰 목록을 조회하는 API")
    public ResponseEntity<ApiResponse<Page<ReviewResponseDto>>> getReviewsByAccommodation(
            @PathVariable Long accommodationId, Pageable pageable) {
        Page<ReviewResponseDto> reviews = reviewService.getReviewsByAccommodation(accommodationId, pageable);
        return ResponseEntity.ok(ApiResponse.success("리뷰 조회 성공", reviews));
    }

}
