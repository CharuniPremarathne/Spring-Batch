package com.example.camelProject.eventListners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.OnReadError;

public class CustomItemReadListenerAnt {

    private static final Logger logger = LoggerFactory.getLogger(CustomItemReadListenerAnt.class);

    @BeforeRead
    public void beforeRead(){
        logger.info("===Before read the record");
    }

    @AfterRead
    public void afterRead(){
        logger.info("===After read the record");
    }

    @OnReadError
    public void onReadError(){
        logger.info("===Error during reading");
    }
}
