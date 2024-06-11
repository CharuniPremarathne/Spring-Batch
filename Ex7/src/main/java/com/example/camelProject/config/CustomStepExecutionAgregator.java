package com.example.camelProject.config;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.StepExecutionAggregator;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomStepExecutionAgregator implements StepExecutionAggregator {
    @Override
    public void aggregate(StepExecution stepExecution, Collection<StepExecution> collection) {
        int totalProcessed = collection.stream()
                .mapToInt(StepExecution::getWriteCount)
                .sum();
        stepExecution.setWriteCount(totalProcessed);
    }
}
