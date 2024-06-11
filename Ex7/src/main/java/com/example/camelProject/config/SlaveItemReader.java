package com.example.camelProject.config;

import com.example.camelProject.model.StudentNew;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@StepScope
public class SlaveItemReader implements ItemReader<StudentNew> {

    @Value("#{stepExecutionContext['start']}")
    private int start;

    @Value("#{stepExecutionContext['end']}")
    private int end;

    private FlatFileItemReader<StudentNew> flatFileItemReader;
    private int currentIndex;

    @PostConstruct
    public void init() {
        flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/data.csv"));
        flatFileItemReader.setLinesToSkip(start + 1); // Skip header and lines before the start index
        flatFileItemReader.setLineMapper(lineMapper());
        currentIndex = start;
    }

    @Override
    public StudentNew read() throws Exception {
        if (currentIndex <= end) {
            StudentNew student = flatFileItemReader.read();
            if (student != null) {
                currentIndex++;
            }
            return student;
        } else {
            return null;
        }
    }

    private LineMapper<StudentNew> lineMapper() {
        DefaultLineMapper<StudentNew> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("studentid", "studentname");

        BeanWrapperFieldSetMapper<StudentNew> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(StudentNew.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
