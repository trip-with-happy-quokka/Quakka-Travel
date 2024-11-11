package com.sparta.quokkatravel.domain.common.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Profile("local")
@Slf4j
@Configuration
public class MetaDBConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties quokkaDataSourceProperties() {
        log.info("Configuring quokka_travel datasource properties");
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.batch.datasource")
    public DataSourceProperties batchDataSourceProperties() {
        log.info("Configuring batch_metaDB datasource properties");
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }

    @Bean
    public DataSource routingDataSource() {

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("quokka", quokkaDataSource());
        dataSourceMap.put("batch", batchDataSource());

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(quokkaDataSource());

        return routingDataSource;
    }

    @Bean
    public DataSource quokkaDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(quokkaDataSourceProperties().getUrl());
        dataSource.setUsername(quokkaDataSourceProperties().getUsername());
        dataSource.setPassword(quokkaDataSourceProperties().getPassword());
        dataSource.setDriverClassName(quokkaDataSourceProperties().getDriverClassName());
        return dataSource;
    }

    @Bean
    public DataSource batchDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(batchDataSourceProperties().getUrl());
        dataSource.setUsername(batchDataSourceProperties().getUsername());
        dataSource.setPassword(batchDataSourceProperties().getPassword());
        dataSource.setDriverClassName(batchDataSourceProperties().getDriverClassName());
        return dataSource;
    }
}
