package com.sparta.quokkatravel.domain.common.elasticsearch.processor;

import com.sparta.quokkatravel.domain.common.elasticsearch.mapper.EntityToDocumentMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

public interface EntityEventProcessor<E, D, ID> {
    CrudRepository<E, ID> getRepository();
    ElasticsearchRepository<D, ID> getElasticsearchRepository();
    EntityToDocumentMapper<E, D> getMapper();
}
