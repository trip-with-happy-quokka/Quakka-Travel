package com.sparta.quokkatravel.domain.search.repository;

import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "com/sparta/quokkatravel/domain/search/repository")
public interface CouponSearchRepository extends ElasticsearchRepository<CouponDocument, String> {

    Page<AccommodationDocument> findByNameContaining(String keyword, Pageable pageable);
}
