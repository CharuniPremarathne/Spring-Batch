package com.example.camelProject.config;

import com.example.camelProject.model.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

//@Component
public class ProjectItemProcessor implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student student) throws Exception {
        student.setStudentname(student.getStudentname()+"New");
        return student;
    }

//   // private String transformName(String studentname) {
//        return studentname.toUpperCase();
//    }
}
