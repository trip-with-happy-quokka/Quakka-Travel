package com.sparta.quokkatravel.domain.common.elasticsearch.processor;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.common.elasticsearch.mapper.AccommodationMapper;
import com.sparta.quokkatravel.domain.common.elasticsearch.mapper.EntityToDocumentMapper;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.repository.AccommodationSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccommodationEventProcessor implements EntityEventProcessor<Accommodation, AccommodationDocument, Long> {

    private AccommodationRepository accommodationRepository;
    private AccommodationSearchRepository accommodationSearchRepository;
    private AccommodationMapper accommodationMapper;

    @Override
    public CrudRepository<Accommodation, Long> getRepository() {
        return accommodationRepository;
    }

    @Override
    public ElasticsearchRepository<AccommodationDocument, Long> getElasticsearchRepository() {
        return accommodationSearchRepository;
    }

    @Override
    public EntityToDocumentMapper<Accommodation, AccommodationDocument> getMapper() {
        return accommodationMapper;
    }
}

