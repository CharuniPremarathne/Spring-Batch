package com.example.camelProject.eventListners;

import com.example.camelProject.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class CustomItemWriteListener implements ItemWriteListener<Project> {

    private static final Logger logger = LoggerFactory.getLogger(CustomItemWriteListener.class);

    @Override
    public void beforeWrite(List<? extends Project> list) {
        logger.info("===Writing started list");
    }

    @Override
    public void afterWrite(List<? extends Project> list) {
        logger.info("===Writing completed");
    }

    @Override
    public void onWriteError(Exception e, List<? extends Project> list) {
        logger.info("===Error in reading person record");
    }
}
