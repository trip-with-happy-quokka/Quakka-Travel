package com.sparta.quokkatravel.domain.search.service;

import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.dto.SearchAccommodationRes;
import com.sparta.quokkatravel.domain.search.repository.AccommodationSearchRepository;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final ServerProperties serverProperties;
    private final AccommodationSearchRepository accommodationSearchRepository;

    public SearchServiceImpl(ServerProperties serverProperties, AccommodationSearchRepository accommodationSearchRepository) {
        this.serverProperties = serverProperties;
        this.accommodationSearchRepository = accommodationSearchRepository;
    }

    @Override
    public List<SearchAccommodationRes> searchAccommodatiosByName(String keyword, Pageable pageable) {
        return accommodationSearchRepository.findByNameContaining(keyword, pageable).stream().map(SearchAccommodationRes::new).toList();
    }

    @Override
    public List<SearchAccommodationRes> searchAccommodatiosByAddress(String keyword, Pageable pageable) {
        return accommodationSearchRepository.findByAddressContaining(keyword, pageable).stream().map(SearchAccommodationRes::new).toList();
    }
}
