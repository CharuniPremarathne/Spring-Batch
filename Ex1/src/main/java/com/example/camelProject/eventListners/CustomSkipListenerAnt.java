package com.example.camelProject.eventListners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;

public class CustomSkipListenerAnt {
    private static final Logger logger = LoggerFactory.getLogger(CustomSkipListenerAnt.class);

    @OnSkipInRead
    public void onSkipRead(){
        logger.info("===Skipped During reading");
    }

    @OnSkipInWrite
    public void onSkipWrite(){
        logger.info("===Skipped During writing");
    }

    @OnSkipInProcess
    public void onSkipProcess(){
        logger.info("===Skipped During processing");
    }
}
