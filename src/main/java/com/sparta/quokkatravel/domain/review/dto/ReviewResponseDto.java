package com.sparta.quokkatravel.domain.review.dto;

import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long id;
    private Long accommodationId;
    private Long userId;
    private Long rating;
    private String content;

    public ReviewResponseDto(Long id, Long accommodationId, Long userId, Long rating, String content){
        this.id = id;
        this.accommodationId = accommodationId;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
    }
}
