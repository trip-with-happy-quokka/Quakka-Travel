package com.sparta.quokkatravel.domain.common.elasticsearch.entity_event;

public class AccommodationUpdatedEvent extends AbstractEntityEvent<Long> {
    public AccommodationUpdatedEvent(Object source, Long accommodationId) {
        super(source, accommodationId);
    }
}
