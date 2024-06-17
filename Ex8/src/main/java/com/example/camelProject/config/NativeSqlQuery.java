package com.example.camelProject.config;

import com.example.camelProject.model.Student;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;

public class NativeSqlQuery extends JpaNativeQueryProvider<Student> {
    public NativeSqlQuery() {
        setSqlQuery("SELECT s.studentid, s.studentname, s.courseid, c.coursename FROM Student s JOIN Course c ON c.courseid = s.courseid");
        setEntityClass(Student.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception{
        super.afterPropertiesSet();
    }
}
