package com.example.camelProject.eventListners;

import com.example.camelProject.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

public class CustomItemProcessListener implements ItemProcessListener<Project, Project> {

    private static final Logger logger = LoggerFactory.getLogger(CustomItemProcessListener.class);

    @Override
    public void beforeProcess(Project project) {
        logger.info("===Record has read " + project);
    }

    @Override
    public void afterProcess(Project project, Project project2) {
        logger.info("===Record processed");
    }

    @Override
    public void onProcessError(Project project, Exception e) {
        logger.info("===Error in reading the person record");
    }
}
