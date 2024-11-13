package com.sparta.quokkatravel.domain.coupon.schedule;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class CouponSchedule {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public CouponSchedule(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    //@Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
    // 매일 오전 12시에 스케줄링
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void runBatchJob() throws Exception{

        System.out.println("coupon schedule start");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("couponGenerationJob"), jobParameters);
    }
}
