//package com.sparta.quokkatravel.domain.common.batch;
//
//import com.sparta.quokkatravel.domain.coupon.entity.Coupon;
//import com.sparta.quokkatravel.domain.coupon.repository.CouponRepository;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.UUID;
//
//// 테이블 복제처리
//@Configuration
//public class CouponGenerationJobConfig {
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager platformTransactionManager;
//    private final CouponRepository couponRepository;
//
//    public CouponGenerationJobConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, CouponRepository couponRepository){
//        this.jobRepository = jobRepository;
//        this.platformTransactionManager = platformTransactionManager;
//        this.couponRepository = couponRepository;
//    }
//
//    @Bean
//    public Job couponGenerationJob() {
//        return new JobBuilder("couponGenerationJob", jobRepository)
//                .start(couponGenerationStep())
//                .build();
//    }
//
//    @Bean
//    public Step couponGenerationStep() {
//        return new StepBuilder("couponGenerationStep", jobRepository)
//                .<Integer, Coupon>chunk(100, platformTransactionManager)
//                .reader(couponCountReader())
//                .processor(couponGeneratorProcessor())
//                .writer(couponWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<Integer> couponCountReader() {
//        return new ItemReader<Integer>() {
//            private int count = 0;
//            private final int targetCount = 100; // 생성할 쿠폰 개수
//
//            @Override
//            public Integer read() {
//                if (count < targetCount) {
//                    return count++;
//                }
//                return null; // null이 반환되면 읽기 종료
//            }
//        };
//    }
//
//    @Bean
//    public ItemProcessor<Integer, Coupon> couponGeneratorProcessor() {
//        return count -> {
//            Coupon coupon = new Coupon();
//            coupon.setCode(UUID.randomUUID().toString()); // 쿠폰 번호를 UUID로 생성
//            return coupon;
//        };
//    }
//
//    @Bean
//    public RepositoryItemWriter<Coupon> couponWriter() {
//        return new RepositoryItemWriterBuilder<Coupon>()
//                .repository(couponRepository)
//                .methodName("save")
//                .build();
//    }
//}