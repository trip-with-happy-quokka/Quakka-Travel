package com.sparta.quokkatravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync

@EnableJpaAuditing
@EnableCaching
@EnableScheduling
@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
@ComponentScan(basePackages = "com.sparta.quokkatravel.domain")

public class QuokkaTravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuokkaTravelApplication.class, args);
    }

}
