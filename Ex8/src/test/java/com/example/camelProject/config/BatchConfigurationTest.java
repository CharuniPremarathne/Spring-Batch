package com.example.camelProject.config;

import com.example.camelProject.model.Student;
import com.example.camelProject.repository.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBatchTest
@Slf4j
class BatchConfigurationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private StudentRepo studentRepo;

    @BeforeClass
    public static void setupClass(){
        log.info("-----@BeforeClass");
    }

    @AfterClass
    public static void afterClass(){
        log.info("-----@AfterClass");
    }

    @Before
    public void setUp(){
        Student student1 = new Student();
        student1.setStudentname("student1");
        studentRepo.save(student1);

        Student student2 = new Student();
        student2.setStudentname("student2");
        studentRepo.save(student2);
    }

    @After
    public void tearDown(){
        log.info("@After");
        studentRepo.deleteAll();
    }

    @Test
    public void testJob() throws Exception{
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}