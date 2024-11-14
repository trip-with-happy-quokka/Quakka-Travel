//package com.sparta.quokkatravel.domain.common.batch;
//
//import com.sparta.quokkatravel.domain.user.entity.User;
//import com.sparta.quokkatravel.domain.user.entity.UserRole;
//import com.sparta.quokkatravel.domain.user.repository.UserRepository;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//
//@Configuration
//public class InactiveUserJobConfig {
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final UserRepository userRepository;
//
//    public InactiveUserJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, UserRepository userRepository) {
//        this.jobRepository = jobRepository;
//        this.transactionManager = transactionManager;
//        this.userRepository = userRepository;
//    }
//
//    @Bean
//    public Job inactiveUserJob() {
//        return new JobBuilder("inactiveUserJob", jobRepository)
//                .start(inactiveUserStep())
//                .build();
//    }
//
//    @Bean
//    public Step inactiveUserStep() {
//        return new StepBuilder("inactiveUserStep", jobRepository)
//                .<User, User>chunk(100, transactionManager)
//                .reader(inactiveUserReader())
//                .processor(inactiveUserProcessor())
//                .writer(inactiveUserWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<User, User> inactiveUserProcessor() {
//        return user -> {
//            user.updateStatus("INACTIVE");
//            user.updateRole(UserRole.INACTIVE);
//            return user;
//        };
//    }
//
//    @Bean
//    public RepositoryItemWriter<User> inactiveUserWriter() {
//        return new RepositoryItemWriterBuilder<User>()
//                .repository(userRepository)
//                .methodName("save")
//                .build();
//    }
//
//    @Bean
//    public org.springframework.batch.item.ItemReader<User> inactiveUserReader() {
//        LocalDate targetDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
//        return () -> userRepository.findAll().stream()
//                .filter(user -> user.getUpdatedAt().toLocalDate().isBefore(targetDate))
//                .filter(user -> !UserRole.INACTIVE.equals(user.getUserRole()))
//                .findFirst()
//                .orElse(null);
//    }
//}
