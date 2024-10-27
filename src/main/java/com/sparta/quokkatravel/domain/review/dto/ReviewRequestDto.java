package com.sparta.quokkatravel.domain.review.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ReviewRequestDto {

    private Long accommodationId;
    private Long rating;
    private String content;

    public ReviewRequestDto(Long accommodationId, Long rating, String content){
        this.accommodationId = accommodationId;
        this.rating = rating;
        this.content = content;
    }

}
