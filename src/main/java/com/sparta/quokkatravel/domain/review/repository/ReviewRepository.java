package com.sparta.quokkatravel.domain.review.repository;

import com.sparta.quokkatravel.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 특정 숙소에 대한 리뷰 목록을 페이징 처리하여 조회
    Page<Review> findByAccommodation_Id(Long accommodationId, Pageable pageable);
}