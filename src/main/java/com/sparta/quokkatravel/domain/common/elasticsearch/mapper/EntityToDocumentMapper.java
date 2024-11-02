package com.sparta.quokkatravel.domain.common.elasticsearch.mapper;

public interface EntityToDocumentMapper<E, D> {
    D toDocument(E entity);
}
