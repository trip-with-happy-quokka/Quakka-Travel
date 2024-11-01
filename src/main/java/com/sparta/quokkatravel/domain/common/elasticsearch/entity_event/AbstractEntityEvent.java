package com.sparta.quokkatravel.domain.common.elasticsearch.entity_event;

import org.springframework.context.ApplicationEvent;

public abstract class AbstractEntityEvent<T> extends ApplicationEvent {
    private final T entityId;

    public AbstractEntityEvent(Object source, T entityId) {
        super(source);
        this.entityId = entityId;
    }

    public T getEntityId() {
        return entityId;
    }
}

