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
public class LocalDataSourceConfig {

    @Bean
    @Primary
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
    @Bean(name = "dataSource")
    public DataSource quokkaDataSource() {
        return new LazyConnectionDataSourceProxy(
                quokkaDataSourceProperties().initializeDataSourceBuilder()
                        .type(HikariDataSource.class).build()
        );
    }

    @Bean(name = "batchDataSource")
    public DataSource batchDataSource() {
        return new LazyConnectionDataSourceProxy(
                batchDataSourceProperties().initializeDataSourceBuilder()
                        .type(HikariDataSource.class).build()
        );
    }
}