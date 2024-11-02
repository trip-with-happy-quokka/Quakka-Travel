package com.sparta.quokkatravel.domain.search.repository;

import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.user.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@EnableElasticsearchRepositories(basePackages = "com/sparta/quokkatravel/domain/search/repository")
public interface AccommodationSearchRepository extends ElasticsearchRepository<AccommodationDocument, Long> {

    List<AccommodationDocument> findByNameAndAddressAndRating(String name, String address, Long rating);

    Optional<AccommodationDocument> findByAccommodationId(Long accommodationId);

    default AccommodationDocument findByAccommodationIdOrElseThrow(Long accommodationId) {
        return findByAccommodationId(accommodationId).orElseThrow(() -> new NotFoundException("AccommodationDocument Not Found"));
    }
}
