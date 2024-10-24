package com.sparta.quokkatravel.domain.review.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class ReviewRequestDto {

    private Long accomodationId;
    private Long userId;
    private Long rating;
    private String content;

    public ReviewRequestDto(Long accomodationId, Long userId, Long rating, String content){
        this.accomodationId = accomodationId;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
    }

}
