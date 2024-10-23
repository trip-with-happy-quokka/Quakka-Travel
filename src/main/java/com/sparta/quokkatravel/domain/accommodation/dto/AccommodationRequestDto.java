package com.sparta.quokkatravel.domain.accommodation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccommodationRequestDto {

    private String name;
    private String description;
    private String address;

    public AccommodationRequestDto(String name, String description, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }
}
