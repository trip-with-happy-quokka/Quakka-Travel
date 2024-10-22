package com.sparta.quokkatravel.domain.review.repository;

import com.sparta.quokkatravel.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
