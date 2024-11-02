package com.sparta.quokkatravel.domain.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;


@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUri;
//    @Value("${spring.elasticsearch.username}")
//    private String elasticsearchUsername;
//    @Value("${spring.elasticsearch.password}")
//    private String elasticsearchPassword;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUri)
//                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();
    }
}
