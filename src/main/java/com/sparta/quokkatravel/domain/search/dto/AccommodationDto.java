package com.sparta.quokkatravel.domain.search.dto;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDto {

    private String name;

    public AccommodationDto(Accommodation accommodation) {
        this.name = accommodation.getName();
    }

}
