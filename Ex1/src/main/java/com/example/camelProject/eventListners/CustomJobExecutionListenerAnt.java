package com.example.camelProject.eventListners;

import com.example.camelProject.config.BatchConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class CustomJobExecutionListenerAnt {

    private static final Logger logger = LoggerFactory.getLogger(CustomJobExecutionListenerAnt.class);

    @BeforeJob
    public void beforeJob(JobExecution jobExecution){
        logger.info("===Job is about to start" + jobExecution.getJobId());
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution){
        logger.info("====Job finished" + jobExecution.getStatus());
    }


}
