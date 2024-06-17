package com.example.camelProject.eventListners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.annotation.BeforeStep;

public class CustomStepExecutionListenerAnt {

    private static final Logger logger = LoggerFactory.getLogger(CustomStepExecutionListenerAnt.class);

    @BeforeStep
    public void beforeStep(StepExecution stepExecution){
        logger.info("===Step is about to start" + stepExecution.getStepName());
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution){
        logger.info("====Step finished" + stepExecution.getStatus());
    }

}
