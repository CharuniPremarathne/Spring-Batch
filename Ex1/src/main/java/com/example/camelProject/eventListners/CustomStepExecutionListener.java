package com.example.camelProject.eventListners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class CustomStepExecutionListener implements StepExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomStepExecutionListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("===Step " + stepExecution.getStepName()+ " is about to start");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("===Step " + stepExecution.getStepName() + " " + stepExecution.getExitStatus());
        return stepExecution.getExitStatus();
    }

    public CustomStepExecutionListener() {
    }
}
