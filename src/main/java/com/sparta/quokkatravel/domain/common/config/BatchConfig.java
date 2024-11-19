//package com.sparta.quokkatravel.domain.common.config;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class BatchConfig {
//
//    @Bean
//    public ApplicationRunner jobRunner(JobLauncher jobLauncher,
//                                       @Qualifier("couponGenerationJob") Job couponGenerationJob,
//                                       @Qualifier("inactiveUserJob") Job inactiveUserJob) {
//        return args -> {
//            jobLauncher.run(couponGenerationJob, new JobParameters());
//            jobLauncher.run(inactiveUserJob, new JobParameters());
//        };
//    }
//}
