package com.example.camelProject.eventListners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;

public class CustomChunkExecutionListenerAnt {

    private static final Logger logger = LoggerFactory.getLogger(CustomChunkExecutionListenerAnt.class);

    @BeforeChunk
    public void beforeChunk(){
        logger.info("===Before chunk processing");
    }

    @AfterChunk
    public void afterChunk(){
        logger.info("===After Chunk");
    }

    @AfterChunkError
    public void afterChunkError(){
        logger.info("===After chunk error");
    }

}
