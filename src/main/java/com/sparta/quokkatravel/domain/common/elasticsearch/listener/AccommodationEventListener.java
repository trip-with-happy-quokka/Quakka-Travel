package com.sparta.quokkatravel.domain.common.elasticsearch.listener;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.common.elasticsearch.processor.AccommodationEventProcessor;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import org.springframework.stereotype.Component;

@Component
public class AccommodationEventListener extends AbstractEntityEventListener<Accommodation, AccommodationDocument, Long> {
    public AccommodationEventListener(AccommodationEventProcessor processor) {
        super(processor);
    }
}
