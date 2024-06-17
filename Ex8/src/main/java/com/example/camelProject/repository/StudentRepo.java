package com.example.camelProject.repository;

import com.example.camelProject.model.Course;
import com.example.camelProject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {


}
