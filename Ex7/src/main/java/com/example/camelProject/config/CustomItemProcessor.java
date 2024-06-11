package com.example.camelProject.config;

import com.example.camelProject.model.StudentNew;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessor implements ItemProcessor<StudentNew, StudentNew> {
    @Override
    public StudentNew process(StudentNew student) throws Exception {

        return student;
    }

//   // private String transformName(String studentname) {
//        return studentname.toUpperCase();
//    }
}
