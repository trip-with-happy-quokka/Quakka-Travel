package com.sparta.quokkatravel.domain.user.schedule;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class InactiveUserSchedule {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public InactiveUserSchedule(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
    //@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void runInactiveUserJob() throws Exception {
        System.out.println("Starting inactive user job");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("inactiveUserJob"), jobParameters);
    }
}