package com.example.camelProject.config;

import com.example.camelProject.model.Student;
import com.example.camelProject.repository.StudentRepo;
import com.example.camelProject.step.FileCollector;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private StudentRepo studentRepo;

    @Autowired
    private FileCollector fileCollector;

    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<Student> reader(EntityManagerFactory entityManagerFactory){
        JpaPagingItemReader<Student> itemReader = new JpaPagingItemReader<>();
        itemReader.setEntityManagerFactory(entityManagerFactory);
        itemReader.setQueryString("select s from Student s");
        itemReader.setPageSize(10);
        return itemReader;
    }

    @Bean
    public ProjectItemProcessor processor(){
        return new ProjectItemProcessor();
    }

    @Bean
    public ProjectItemProcessor2 processor2(){return new ProjectItemProcessor2();}

    @Bean
    public ItemWriter<Student> writer(StudentRepo studentRepo){
//        RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
//        writer.setRepository(studentRepo);
//        writer.setMethodName("save");
        return items->{
            for (Student item: items){
                studentRepo.save(item);
            }
        };
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1").<Student, Student> chunk(1)
                .reader(reader(entityManagerFactory))
                .processor(processor())
                .writer(writer(studentRepo))
                .faultTolerant()
                .skipLimit(5)
                .skip(NullPointerException.class)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean Step step2(){
        return stepBuilderFactory.get("step2").<Student, Student> chunk(1)
                .reader(reader(entityManagerFactory))
                .processor(processor2())
                .writer(writer(studentRepo))
                .faultTolerant()
                .skipLimit(5)
                .skip(NullPointerException.class)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job1")
                .start(step1())
                .next(step2())
                .next(fileCollectorStep())
                .build();
    }

    //step2
    @Bean
    public Step fileCollectorStep(){
        return stepBuilderFactory.get("fileCollector")
                .tasklet(fileCollector)
                .build();
    }
}
