package com.example.camelProject.config;


import com.example.camelProject.model.Course;
import com.example.camelProject.model.Student;
import com.example.camelProject.repository.StudentRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private StudentRepo studentRepo;

    private EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<Student> reader() {
        JpaPagingItemReader<Student> itemReader = new JpaPagingItemReader<>();
        itemReader.setEntityManagerFactory(entityManagerFactory);
        //itemReader.setQueryString("select s.studentid, s.studentname from Student s join Course c on c.courseid = s.course.courseid");
        NativeSqlQuery sqlQuery = new NativeSqlQuery();
        itemReader.setQueryProvider(sqlQuery);
        itemReader.setPageSize(10);
        return itemReader;
    }



    @Bean
    public ProjectItemProcessor processor() {
        return new ProjectItemProcessor();
    }

    @Bean
    public ItemWriter<Student> writer(StudentRepo studentRepo) {
//        RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
//        writer.setRepository(studentRepo);
//        writer.setMethodName("save");
//        return writer;
        return items -> {
            for (Student item : items) {
                studentRepo.save(item);
            }
        };
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step1").<Student, Student>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer(studentRepo))
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job1")
                .flow(step()).end().build();
    }
}
