package com.sparta.quokkatravel.domain.search.dto;

import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchAccommodationRes {

    private String name;
    private String address;
    private Long rating;
    private String imageurl;

    public SearchAccommodationRes(AccommodationDocument accommodationDocument) {
        this.name = accommodationDocument.getName();
        this.address = accommodationDocument.getAddress();
        this.rating = accommodationDocument.getRating();
        this.imageurl = accommodationDocument.getImageurl();
    }
}
