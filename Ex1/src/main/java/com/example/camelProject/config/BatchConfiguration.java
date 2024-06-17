package com.example.camelProject.config;

import com.example.camelProject.ProjectResultRowMapper;
import com.example.camelProject.eventListners.*;
import com.example.camelProject.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Autowired
    private JobBuilderFactory jobBuiderFactory;


    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Project> reader() {
        logger.info("=========Initializing JdbcCursorItemReader");
        JdbcCursorItemReader<Project> reader = new JdbcCursorItemReader<Project>();
        reader.setDataSource(dataSource);
        logger.info("=======datasource",dataSource);
        reader.setSql("select projectid, projectcode, projectname from project");
        reader.setRowMapper(new ProjectResultRowMapper());
        return reader;
    }

    @Bean
    public FlatFileItemWriter<Project> writer() {
        logger.info("========Initializing FlatFileItemWriter");
        FlatFileItemWriter<Project> writer = new FlatFileItemWriter<Project>();
        writer.setResource(new FileSystemResource("/home/kpremarathne/Desktop/Trainings/Ramp Up/output.csv"));
        DelimitedLineAggregator<Project> aggregator = new DelimitedLineAggregator<>();
        BeanWrapperFieldExtractor<Project> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"projectid","projectcode", "projectname"});
        aggregator.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(aggregator);
        return writer;
    }

    @Bean
    public Step executeStep() {
        StepBuilder stepBuilder = stepBuilderFactory.get("executeStep");
        SimpleStepBuilder<Project, Project> project = stepBuilder.chunk(1);
        stepBuilder.listener(new CustomStepExecutionListener());
        //stepBuilder.listener(new CustomChunkExecutionListener());
        //stepBuilder.listener(new CustomItemReadListener());
        //stepBuilder.listener(new CustomItemProcessListener());
        //stepBuilder.listener(new CustomItemWriteListener());
        //stepBuilder.listener(new CustomSkipListener());
        return project.reader(reader()).writer(writer()).build();
    }

    @Bean
    public Job processJob() {
        logger.info("======Job execution");
        JobBuilder jobBuilder = jobBuiderFactory.get("processJob");
        jobBuilder.incrementer(new RunIdIncrementer());
        jobBuilder.listener(new CustomJobExecutionListener());
        FlowJobBuilder jobBuilder1 = jobBuilder.flow(executeStep()).end();
        Job job = jobBuilder1.build();
        return job;
    }

}
