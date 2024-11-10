package com.sparta.quokkatravel.domain.common.elasticsearch.entity_event;

public class AccommodationCreatedEvent extends AbstractEntityEvent<Long> {
    public AccommodationCreatedEvent(Object source, Long accommodationId) {
        super(source, accommodationId);
    }

}
