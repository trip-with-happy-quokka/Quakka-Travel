package com.sparta.quokkatravel.domain.search.repository;

import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;
@EnableElasticsearchRepositories(basePackages = "com/sparta/quokkatravel/domain/search/repository")
public interface AccommodationSearchRepository extends ElasticsearchRepository<AccommodationDocument, Long> {

    List<AccommodationDocument> findByNameAndAddressAndRating(String name, String address, Long rating);
}
