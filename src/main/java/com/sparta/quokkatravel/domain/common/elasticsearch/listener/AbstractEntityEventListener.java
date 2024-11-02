package com.sparta.quokkatravel.domain.common.elasticsearch.listener;

import com.sparta.quokkatravel.domain.common.elasticsearch.entity_event.*;
import com.sparta.quokkatravel.domain.common.elasticsearch.entity_event.marker.CreatedEvent;
import com.sparta.quokkatravel.domain.common.elasticsearch.entity_event.marker.DeletedEvent;
import com.sparta.quokkatravel.domain.common.elasticsearch.entity_event.marker.UpdatedEvent;
import com.sparta.quokkatravel.domain.common.elasticsearch.processor.EntityEventProcessor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.event.EventListener;

public abstract class AbstractEntityEventListener<E, D, ID> {

    private final EntityEventProcessor<E, D, ID> processor;

    protected AbstractEntityEventListener(EntityEventProcessor<E, D, ID> processor) {
        this.processor = processor;
    }

    @EventListener
    public void handleEvent(AbstractEntityEvent<ID> event) {
        if (event instanceof CreatedEvent) {
            E entity = processor.getRepository().findById(event.getEntityId())
                    .orElseThrow(() -> new EntityNotFoundException(String.valueOf(event.getEntityId())));
            D document = processor.getMapper().toDocument(entity);
            processor.getElasticsearchRepository().save(document);
        } else if (event instanceof UpdatedEvent) {
            E entity = processor.getRepository().findById(event.getEntityId())
                    .orElseThrow(() -> new EntityNotFoundException(String.valueOf(event.getEntityId())));
            D document = processor.getMapper().toDocument(entity);
            processor.getElasticsearchRepository().save(document);
        } else if (event instanceof DeletedEvent) {
            processor.getElasticsearchRepository().deleteById(event.getEntityId());
        }
    }
}
