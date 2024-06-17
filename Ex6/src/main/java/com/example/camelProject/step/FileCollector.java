package com.example.camelProject.step;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
@Slf4j
public class FileCollector implements Tasklet {


    private String dataDirectory = "/home/kpremarathne/Desktop/files";

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
    log.info("=========tasklet execution");
        Path dataPath = Paths.get(dataDirectory);

        try(Stream<Path> filesToDelete = Files.walk(dataPath)){
            filesToDelete.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        return RepeatStatus.FINISHED;
    }
}
