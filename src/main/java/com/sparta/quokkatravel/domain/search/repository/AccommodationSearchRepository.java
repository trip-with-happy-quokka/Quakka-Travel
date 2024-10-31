package com.sparta.quokkatravel.domain.search.repository;

import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;
@EnableElasticsearchRepositories(basePackages = "com/sparta/quokkatravel/domain/search/repository")
public interface AccommodationSearchRepository extends ElasticsearchRepository<AccommodationDocument, String> {

    Page<AccommodationDocument> findByNameContaining(String keyword, Pageable pageable);
    Page<AccommodationDocument> findByAddressContaining(String keyword, Pageable pageable);
}
