package com.example.camelProject.eventListners;

import com.example.camelProject.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class CustomItemReadListener implements ItemReadListener<Project> {

    private static final Logger logger = LoggerFactory.getLogger(CustomItemReadListener.class);

    @Override
    public void beforeRead() {
        logger.info("===Reading a new Record");
    }

    @Override
    public void afterRead(Project project) {
    logger.info("===New Record read ");
    }

    @Override
    public void onReadError(Exception e) {
    logger.info("===Error in reading new record");
    }
}
