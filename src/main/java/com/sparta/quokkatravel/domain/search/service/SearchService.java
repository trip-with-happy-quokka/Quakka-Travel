package com.sparta.quokkatravel.domain.search.service;

import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.dto.SearchAccommodationRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {

    List<SearchAccommodationRes> searchAccommodatiosByName(String keyword, Pageable pageable);
    List<SearchAccommodationRes> searchAccommodatiosByAddress(String keyword, Pageable pageable);
}
