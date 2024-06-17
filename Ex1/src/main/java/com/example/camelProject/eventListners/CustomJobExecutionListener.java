package com.example.camelProject.eventListners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;


public class CustomJobExecutionListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomJobExecutionListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("===Job ID " + jobExecution.getJobId() + " is about to start");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("===Job has executed with the status " + jobExecution.getStatus());
    }

    public CustomJobExecutionListener() {
    }
}
