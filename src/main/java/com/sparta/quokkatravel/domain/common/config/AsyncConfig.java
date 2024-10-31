package com.sparta.quokkatravel.domain.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // 비동기 처리를 활성화
@EnableAspectJAutoProxy
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 기본 스레드 수
        executor.setMaxPoolSize(20); // 최대 스레드 수
        executor.setQueueCapacity(500); // 대기 큐의 크기
        executor.setThreadNamePrefix("EmailThread-");
        executor.initialize();

        // 스레드 풀의 상태를 로그로 출력
        logThreadPoolStatus(executor);

        return executor;
    }

    private void logThreadPoolStatus(ThreadPoolTaskExecutor executor) {
        System.out.println("Active Threads: " + executor.getActiveCount());
        System.out.println("Total Tasks: " + executor.getThreadPoolExecutor().getTaskCount());
        System.out.println("Completed Tasks: " + executor.getThreadPoolExecutor().getCompletedTaskCount());
    }

}
