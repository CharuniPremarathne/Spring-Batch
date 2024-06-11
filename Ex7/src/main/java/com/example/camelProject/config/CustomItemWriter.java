package com.example.camelProject.config;

import com.example.camelProject.model.StudentNew;
import com.example.camelProject.repository.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CustomItemWriter implements ItemWriter<StudentNew> {

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public void write(List<? extends StudentNew> list) throws Exception {
        log.info("Thread Name : " + Thread.currentThread().getName());
        studentRepo.saveAll(list);
    }
}
