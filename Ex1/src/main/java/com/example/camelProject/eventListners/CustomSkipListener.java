package com.example.camelProject.eventListners;

import com.example.camelProject.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener<Project, Project> {

    private static final Logger logger = LoggerFactory.getLogger(CustomSkipListener.class);

    @Override
    public void onSkipInRead(Throwable throwable) {
        logger.info("===Skipped During reading");
    }

    @Override
    public void onSkipInWrite(Project project, Throwable throwable) {
        logger.info("===Skipped During writing");
    }

    @Override
    public void onSkipInProcess(Project project, Throwable throwable) {
        logger.info("===Skipped During processing");
    }
}
