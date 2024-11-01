package com.sparta.quokkatravel.domain.common.elasticsearch.mapper;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import org.springframework.stereotype.Component;

@Component
public class AccommodationMapper implements EntityToDocumentMapper<Accommodation, AccommodationDocument> {

    @Override
    public AccommodationDocument toDocument(Accommodation entity) {
        return new AccommodationDocument(entity);
    }
}
