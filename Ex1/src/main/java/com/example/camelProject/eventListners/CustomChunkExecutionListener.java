package com.example.camelProject.eventListners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class CustomChunkExecutionListener implements ChunkListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomChunkExecutionListener.class);

    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        logger.info("====Before Chunk Execution");
    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {
        logger.info("====After Chunk Execution");
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        logger.info("===Error during chunk processing");
    }
}
