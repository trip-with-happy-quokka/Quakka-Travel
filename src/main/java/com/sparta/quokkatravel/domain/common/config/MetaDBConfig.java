package com.sparta.quokkatravel.domain.common.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class MetaDBConfig {

    // 메타 데이터베이스 설정 (배치 메타데이터용)
    @Bean
    @ConfigurationProperties(prefix = "spring.batch.datasource")
    public DataSourceProperties metaDataSourceProperties() {
        log.info("Setting up Batch Meta DataSource Properties");
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource metaDataSource() {
        log.info("Setting up Batch Meta DataSource");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(metaDataSourceProperties().getUrl());
        dataSource.setUsername(metaDataSourceProperties().getUsername());
        dataSource.setPassword(metaDataSourceProperties().getPassword());
        dataSource.setDriverClassName(metaDataSourceProperties().getDriverClassName());
        return dataSource;
    }
}
