package com.example.camelProject.eventListners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnWriteError;

public class CustomItemWriteListenerAnt {

    private static final Logger logger = LoggerFactory.getLogger(CustomItemWriteListenerAnt.class);

    @BeforeWrite
    public void beforeWrite(){
        logger.info("===Writing started list");
    }

    @AfterWrite
    public void afterWrite(){
        logger.info("===Writing completed");
    }

    @OnWriteError
    public void onWriteError(){
        logger.info("===Error in reading person record");
    }
}
