package com.sparta.quokkatravel.domain.common.elasticsearch.entity_event;

public class AccommodationDeletedEvent extends AbstractEntityEvent<Long> {
    public AccommodationDeletedEvent(Object source, Long accommodationId) {
        super(source, accommodationId);
    }
}
