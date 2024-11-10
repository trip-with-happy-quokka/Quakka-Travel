//package com.sparta.quokkatravel.domain.common.batch;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class EventJob {
//
//    @Bean
//    public Job eventJob(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
//        return new JobBuilder("eventJob", jobRepository)
//                .start(eventStep(transactionManager, jobRepository))
//                .build();
//    }
//
//    @Bean
//    public Step eventStep(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
//        return new StepBuilder("eventStep", jobRepository)
//                .tasklet(eventTasklet(), transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Tasklet eventTasklet() {
//        return ((contribution, chunkContext) -> {
//            log.info("EventJob started");
//            return RepeatStatus.FINISHED;
//        });
//    }
//}
//
