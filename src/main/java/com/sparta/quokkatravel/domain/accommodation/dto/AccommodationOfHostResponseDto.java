package com.sparta.quokkatravel.domain.accommodation.dto;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccommodationOfHostResponseDto {

    private Long accommodationId;
    private String name;
    private String description;
    private String address;
    private int rating;

    public AccommodationOfHostResponseDto(Accommodation accommodation) {
        this.accommodationId = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.address = accommodation.getAddress();
        this.rating = accommodation.getRating();
    }
}
