package com.example.camelProject.eventListners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;

public class CustomItemProcessListenerAnt {

    private static final Logger logger = LoggerFactory.getLogger(CustomItemProcessListenerAnt.class);

    @BeforeProcess
    public void beforeProcess(){
        logger.info("===Record reading");
    }

    @AfterProcess
    public void afterProcess(){
        logger.info("===Record has read");
    }

    @OnProcessError
    public void onProcessError(){
        logger.info("===Error during record reading");
    }

}
