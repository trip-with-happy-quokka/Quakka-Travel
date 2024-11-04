package com.sparta.quokkatravel.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchAccommodationRequestDto {
    private String name;
    private String address;
    private Long rating;
}
