package com.example.camelProject.config;

import com.example.camelProject.model.Student;
import org.springframework.batch.item.ItemProcessor;

public class ProjectItemProcessor2 implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student student) throws Exception {
        student.setStudentname("New" + student.getStudentname());
        return student;
    }
}
