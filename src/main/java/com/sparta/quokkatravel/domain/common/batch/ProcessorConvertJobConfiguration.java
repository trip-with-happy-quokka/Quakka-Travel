//package com.sparta.quokkatravel.domain.common.batch;
//
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class ProcessorConvertJobConfiguration {
//
//    public static final String JOB_NAME = "processorConvertBatch";
//    public static final String BEAN_PREFIX = JOB_NAME + "_";
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final EntityManagerFactory entityManagerFactory;
//
//    @Value("${ChunkSize}:1000")
//    private int chunksize;
//}
